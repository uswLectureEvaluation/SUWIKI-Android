package com.kunize.uswtimetable.ui.lecture_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import com.kunize.uswtimetable.dataclass.LectureInfoData
import com.kunize.uswtimetable.ui.evaluation.TempEvaluationViewModel
import com.kunize.uswtimetable.ui.repository.lecture_info.LectureInfoRepository
import kotlinx.coroutines.launch

class LectureInfoViewModel(private val lectureInfoRepository: LectureInfoRepository) : ViewModel() {
    //이수 구분
    private val _lectureType = MutableLiveData<String>()
    val lectureType: LiveData<String>
        get() = _lectureType

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
}