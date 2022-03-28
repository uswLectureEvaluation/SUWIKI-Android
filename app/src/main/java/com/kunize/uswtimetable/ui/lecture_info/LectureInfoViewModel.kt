package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import com.kunize.uswtimetable.ui.repository.lecture_info.LectureInfoRepository
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

    fun examInfoRadioBtnClicked() {
        //TODO
        // 서버로 부터 데이터 불러옴
        // 데이터 결과에 따라 _showHideExamDataLayout 여부 설정
        _showHideExamDataLayout.value = true
        changeWriteBtnText(R.string.write_exam)
    }

    fun lectureInfoRadioBtnClicked() {
        //TODO 서버로 부터 강의평가 데이터 받아오기
        _showHideExamDataLayout.value = false
        _showNoExamDataLayout.value = false
        val tmp = arrayListOf<EvaluationData?>()
        tmp.add(null)
        changeWriteBtnText(R.string.write_evaluation)
    }

    private fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
    }

    fun setInfoValue(lectureId: Long) {
        viewModelScope.launch {
            val response = lectureInfoRepository.getLectureDetailInfo(lectureId)
            if(response.isSuccessful) {
                _lectureDetailInfoData.value = response.body()
            } else {
                //TODO 통신 실패 처리
            }
        }
    }

    fun getEvaluationList(lectureId: Long) {
        viewModelScope.launch {
            val response = lectureInfoRepository.getLectureDetailEvaluation(lectureId, _page.value!!.toInt())
            delay(delayTime)
            if (response.isSuccessful) {
                val tmpEvaluationData = response.body()?.convertToEvaluationData()
                deleteLoading()
                if(!tmpEvaluationData.isNullOrEmpty()) {
                    if(tmpEvaluationData.size == 10)
                        tmpEvaluationData.add(null)
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