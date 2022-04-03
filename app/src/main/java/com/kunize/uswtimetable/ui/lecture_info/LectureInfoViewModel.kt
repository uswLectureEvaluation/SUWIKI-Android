package com.kunize.uswtimetable.ui.lecture_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import com.kunize.uswtimetable.ui.common.BaseInfiniteRecyclerItemViewModel
import com.kunize.uswtimetable.ui.repository.lecture_info.LectureInfoRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LectureInfoViewModel(private val lectureInfoRepository: LectureInfoRepository) : BaseInfiniteRecyclerItemViewModel() {
    private val _writeBtnText = MutableLiveData<Int>()
    val writeBtnText: LiveData<Int>
        get() = _writeBtnText

    private val _showNoExamDataLayout = MutableLiveData<Boolean>()
    val showNoExamDataLayout: LiveData<Boolean>
        get() = _showNoExamDataLayout

    private val _showHideExamDataLayout = MutableLiveData<Boolean>()
    val showHideExamDataLayout: LiveData<Boolean>
        get() = _showHideExamDataLayout

    private val _lectureDetailInfoData = MutableLiveData<LectureDetailInfoDto>()
    val lectureDetailInfoData: LiveData<LectureDetailInfoDto>
        get() = _lectureDetailInfoData

    init {
        _writeBtnText.value = R.string.write_evaluation
        _showNoExamDataLayout.value = false
        _showHideExamDataLayout.value = false
    }

    fun lectureInfoRadioBtnClicked() {
        page.value = 1
        _showHideExamDataLayout.value = false
        _showNoExamDataLayout.value = false
        changeWriteBtnText(R.string.write_evaluation)
        loading()
        getEvaluationList()
    }

    fun examInfoRadioBtnClicked() {
        page.value = 1
        changeWriteBtnText(R.string.write_exam)
        loading()
        getExamList()
    }

    fun usePointBtnClicked() {
        viewModelScope.launch {
            val response = lectureInfoRepository.buyExam(lectureId)
            if(response.isSuccessful) {
                if(response.body() == "success") {
                    _showHideExamDataLayout.value = false
                    page.value = 1
                    loading()
                    getExamList()
                }
            }
        }
    }

    private fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
    }

    suspend fun setInfoValue() {
        withContext(viewModelScope.coroutineContext) {
            val response = lectureInfoRepository.getLectureDetailInfo(lectureId)
            if (response.isSuccessful) {
                _lectureDetailInfoData.value = response.body()
            } else {
                //TODO 통신 실패 처리
            }
        }
    }

    fun scrollBottomEvent() {
        if (page.value!! < 2)
            return
        getLectureList()
    }

    fun getLectureList() {
        when (_writeBtnText.value) {
            R.string.write_evaluation -> getEvaluationList()
            else -> getExamList()
        }
    }

    private fun getEvaluationList() {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailEvaluation(lectureId, page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                deleteLoading()
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                if (!tmpEvaluationData.isNullOrEmpty()) {
                    isLastData(tmpEvaluationData)
                    evaluationList.value!!.addAll(tmpEvaluationData)
                    evaluationList.value = evaluationList.value
                }
                nextPage()
            } else {
                //TODO 통신 실패 로직
            }
        }
    }

    private fun getExamList() {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailExam(lectureId, page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpResponse = response.body()
                val tmpExamData = tmpResponse?.convertToEvaluationData()
                deleteLoading()
                if (tmpExamData != null) {
                    isLastData(tmpExamData)
                    if (tmpExamData.isEmpty() && tmpResponse.examDataExist)
                        _showHideExamDataLayout.value = true
                    else if (!tmpResponse.examDataExist)
                        _showNoExamDataLayout.value = true
                    else {
                        evaluationList.value = tmpExamData
                        nextPage()
                    }
                }
            } else {
                Log.d("lectureApi", "시험정보 클릭 에러 $lectureId,${response.code()}")
            }
        }
    }
}