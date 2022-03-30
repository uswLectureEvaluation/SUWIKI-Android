package com.kunize.uswtimetable.ui.lecture_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import com.kunize.uswtimetable.ui.repository.lecture_info.LectureInfoRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LectureInfoViewModel(private val lectureInfoRepository: LectureInfoRepository) : ViewModel() {
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

    private val _evaluationList = MutableLiveData<ArrayList<EvaluationData?>>()
    val evaluationList: LiveData<ArrayList<EvaluationData?>>
        get() = _evaluationList

    private val _page = MutableLiveData<Int>()

    private val delayTime = 200L

    init {
        _page.value = 1
        _evaluationList.value = arrayListOf(null)
        _writeBtnText.value = R.string.write_evaluation
        _showNoExamDataLayout.value = false
        _showHideExamDataLayout.value = false
    }

    fun usePointBtnClicked() {
        _showHideExamDataLayout.value = false
    }

    fun lectureInfoRadioBtnClicked() {
        _page.value = 1
        _showHideExamDataLayout.value = false
        _showNoExamDataLayout.value = false
        changeWriteBtnText(R.string.write_evaluation)
        _evaluationList.value = arrayListOf(null)
    }

    fun examInfoRadioBtnClicked() {
        changeWriteBtnText(R.string.write_exam)
        _page.value = 1
        _evaluationList.value = arrayListOf(null)
    }

    private fun getExamList(lectureId: Long) {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailExam(lectureId, _page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpExamData = response.body()
                deleteLoading()
                if (tmpExamData != null) {
                    Log.d("lectureApi", "시험정보 클릭 ${_page.value},${tmpExamData.examDataExist}")
                    if(tmpExamData.data.size != 10)
                        _page.value = LAST_PAGE
                    if (tmpExamData.data.isEmpty() && tmpExamData.examDataExist)
                        _showHideExamDataLayout.value = true
                    else if (!tmpExamData.examDataExist)
                        _showNoExamDataLayout.value = true
                    else {
                        //TODO Page 값에 따른 별도 로직 추가, LAST_PAGE 로직 추가
                        _evaluationList.value = tmpExamData.convertToEvaluationData()
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

    fun setInfoValue(lectureId: Long) {
        viewModelScope.launch {
            val response = lectureInfoRepository.getLectureDetailInfo(lectureId)
            if (response.isSuccessful) {
                _lectureDetailInfoData.value = response.body()
            } else {
                //TODO 통신 실패 처리
            }
        }
    }

    fun scrollBottom(lectureId: Long) {
        if (_page.value == LAST_PAGE)
            return
        when (_writeBtnText.value) {
            R.string.write_evaluation -> getEvaluationList(lectureId)
            else -> getExamList(lectureId)
        }
    }

    private fun getEvaluationList(lectureId: Long) {
        viewModelScope.launch {
            val response =
                lectureInfoRepository.getLectureDetailEvaluation(lectureId, _page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                Log.d("lectureApi", "강의평가 클릭 $lectureId,${tmpEvaluationData}")
                deleteLoading()
                if (!tmpEvaluationData.isNullOrEmpty()) {
                    if (tmpEvaluationData.size == 10)
                        tmpEvaluationData.add(null)
                    else
                        _page.value = LAST_PAGE
                    _evaluationList.value!!.addAll(tmpEvaluationData)
                    _evaluationList.value = _evaluationList.value
                }
                nextPage()
            } else {
                _evaluationList.value = arrayListOf()
            }
        }
    }

    private fun nextPage() {
        if (_page.value == LAST_PAGE)
            return
        _page.value = _page.value?.plus(1)
        _page.value = _page.value
    }

    private fun deleteLoading() {
        if (_evaluationList.value?.isEmpty() == true)
            return
        if (_evaluationList.value?.last() == null) {
            _evaluationList.value?.removeLast()
            _evaluationList.value = _evaluationList.value
        }
    }
}