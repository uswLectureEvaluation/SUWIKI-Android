package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureInfoData
import com.kunize.uswtimetable.ui.evaluation.EvaluationViewModel

class LectureInfoViewModel : EvaluationViewModel() {
    //이수 구분
    private val _lectureType = MutableLiveData<String>()
    val lectureType: LiveData<String>
        get() = _lectureType

    //이수 년도 (배열로 받아온다고 가정 ex) {"2021-2", "2020-1"} )
    private val _yearSemesterList = MutableLiveData<String>()
    val yearSemesterList: LiveData<String>
        get() = _yearSemesterList

    private val _infoHoneyScore = MutableLiveData<Float>()
    val infoHoneyScore: LiveData<Float>
        get() = _infoHoneyScore

    private val _infoLearningScore = MutableLiveData<Float>()
    val infoLearningScore: LiveData<Float>
        get() = _infoLearningScore

    private val _infoSatisfactionScore = MutableLiveData<Float>()
    val infoSatisfactionScore: LiveData<Float>
        get() = _infoSatisfactionScore

    private val _infoMeeting = MutableLiveData<String>()
    val infoMeeting: LiveData<String>
        get() = _infoMeeting

    private val _infoTask = MutableLiveData<String>()
    val infoTask: LiveData<String>
        get() = _infoTask

    private val _infoGrade = MutableLiveData<String>()
    val infoGrade: LiveData<String>
        get() = _infoGrade

    private val _writeBtnText = MutableLiveData<Int>()
    val writeBtnText: LiveData<Int>
        get() = _writeBtnText

    private val _showNoExamDataLayout = MutableLiveData<Boolean>()
    val showNoExamDataLayout: LiveData<Boolean>
        get() = _showNoExamDataLayout

    private val _showHideExamDataLayout = MutableLiveData<Boolean>()
    val showHideExamDataLayout: LiveData<Boolean>
        get() = _showHideExamDataLayout

    init {
        setInfoValue()
        _writeBtnText.value = R.string.write_evaluation
        _showNoExamDataLayout.value = false
        _showHideExamDataLayout.value = false
    }

    fun usePointBtnClicked() {
        _showHideExamDataLayout.value = false
        changeData(arrayListOf())
    }

    fun examInfoRadioBtnClicked() {
        //TODO
        // 서버로 부터 데이터 불러옴
        // 데이터 결과에 따라 _showHideExamDataLayout 여부 설정
        _showHideExamDataLayout.value = true
        changeData(arrayListOf())
        changeWriteBtnText(R.string.write_exam)
    }

    fun lectureInfoRadioBtnClicked() {
        //TODO 서버로 부터 강의평가 데이터 받아오기
        _showHideExamDataLayout.value = false
        _showNoExamDataLayout.value = false
        val tmp = arrayListOf<EvaluationData?>()
        tmp.add(null)
        changeData(tmp)
        changeWriteBtnText(R.string.write_evaluation)
    }

    private fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
    }

    fun setInfoValue() {
        val data = LectureInfoData(
            "전핵",
            "2021-2, 2021-1, 2020-2, 2020-1, 2018-1, 2018-2",
            4.2f,
            2.4f,
            3.5f,
            "없음",
            "보통",
            "까다로움"
        )
        _lectureType.value = data.lectureType
        _yearSemesterList.value = data.yearSemesterList
        _infoHoneyScore.value = data.infoHoneyScore
        _infoLearningScore.value = data.infoLearningScore
        _infoSatisfactionScore.value = data.infoSatisfactionScore
        _infoMeeting.value = data.infoMeeting
        _infoTask.value = data.infoTask
        _infoGrade.value = data.infoGrade
    }
}