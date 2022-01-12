package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.LectureInfoData
import com.kunize.uswtimetable.ui.evaluation.EvaluationViewModel

class LectureInfoViewModel : EvaluationViewModel() {
    //이수 구분
    private val _lectureType = MutableLiveData<String>()
    val lectureType: LiveData<String>
        get() = _lectureType

    //이수 년도 (배열로 받아온다고 가정 ex) {"2021-2", "2020-1"} )
    private val _yearSemesterList = MutableLiveData<ArrayList<String>>()
    val yearSemesterList: LiveData<ArrayList<String>>
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

    init {
        setInfoValue(LectureInfoData())
    }

    fun setInfoValue(data: LectureInfoData) {
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