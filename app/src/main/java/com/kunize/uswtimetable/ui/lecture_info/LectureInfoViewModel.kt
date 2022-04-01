package com.kunize.uswtimetable.ui.lecture_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
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
        page.value = 1
        loading()
        _writeBtnText.value = R.string.write_evaluation
        _showNoExamDataLayout.value = false
        _showHideExamDataLayout.value = false
    }

    fun usePointBtnClicked() {
        _showHideExamDataLayout.value = false
    }

    fun lectureInfoRadioBtnClicked() {
        page.value = 1
        _showHideExamDataLayout.value = false
        _showNoExamDataLayout.value = false
        changeWriteBtnText(R.string.write_evaluation)
        evaluationList.value = arrayListOf(null)
    }

    fun examInfoRadioBtnClicked(lectureId: Long) {
        page.value = 1
        changeWriteBtnText(R.string.write_exam)
        evaluationList.value = arrayListOf(null)
        scrollBottomEvent(lectureId)
    }

    private fun getExamList(lectureId: Long) {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailExam(lectureId, page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpExamData = response.body()
                deleteLoading()
                if (tmpExamData != null) {
                    if(tmpExamData.data.size != 10)
                        page.value = LAST_PAGE
                    if (tmpExamData.data.isEmpty() && tmpExamData.examDataExist)
                        _showHideExamDataLayout.value = true
                    else if (!tmpExamData.examDataExist)
                        _showNoExamDataLayout.value = true
                    else {
                        //TODO null 로직 추가
                        evaluationList.value = tmpExamData.convertToEvaluationData()
                        nextPage()
                    }
                }
            } else {
                Log.d("lectureApi", "시험정보 클릭 에러 $lectureId,${response.code()}")
            }
        }
    }

    private fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
    }

    suspend fun setInfoValue(lectureId: Long) {
        withContext(viewModelScope.coroutineContext) {
            val response = lectureInfoRepository.getLectureDetailInfo(lectureId)
            if (response.isSuccessful) {
                _lectureDetailInfoData.value = response.body()
            } else {
                //TODO 통신 실패 처리
            }
        }
    }

    fun scrollBottomEvent(lectureId: Long) {
        if (page.value == LAST_PAGE)
            return
        when (_writeBtnText.value) {
            R.string.write_evaluation -> getEvaluationList(lectureId)
            else -> getExamList(lectureId)
        }
    }

    fun getEvaluationList(lectureId: Long) {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailEvaluation(lectureId, page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                deleteLoading()
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                if (!tmpEvaluationData.isNullOrEmpty()) {
                    if (tmpEvaluationData.size == 10)
                        tmpEvaluationData.add(null)
                    else
                        page.value = LAST_PAGE
                    evaluationList.value!!.addAll(tmpEvaluationData)
                    evaluationList.value = evaluationList.value
                }
                nextPage()
            } else {
                //TODO 통신 실패 로직
            }
        }
    }
}