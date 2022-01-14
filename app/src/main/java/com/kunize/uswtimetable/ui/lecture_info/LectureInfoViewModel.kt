package com.kunize.uswtimetable.ui.lecture_info

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.ExamInfoType
import com.kunize.uswtimetable.dataclass.LectureInfoData
import com.kunize.uswtimetable.dataclass.LectureItemViewType
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment.Companion.dummyData
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

    private val _writeBtnText = MutableLiveData<Int>()
    val writeBtnText: LiveData<Int>
        get() = _writeBtnText

    private val _inflateType = MutableLiveData<Int>()
    val inflateType: LiveData<Int>
        get() = _inflateType

    init {
        setInfoValue(LectureInfoData())
        _writeBtnText.value = R.string.write_evaluation
        _inflateType.value = ExamInfoType.NOT_INFLATE
    }

    fun setInflate(type: Int) {
        _inflateType.value = type
    }

    fun changeWriteBtnText(resource: Int) {
        _writeBtnText.value = resource
    }

    fun onEvaluationRadioBtnClick() {
        clearData()
        _inflateType.value = ExamInfoType.NOT_INFLATE
        setViewType(LectureItemViewType.LECTURE)
        //TODO 서버로 부터 강의평가 데이터 받아오기
        changeData(ArrayList(dummyData.subList(1, 2)))
        changeWriteBtnText(R.string.write_evaluation)
    }

    fun onExamRadioBtnClick() {
        clearData()
        //TODO 서버로부터 시험정보 데이터 받아오기
        //1. 이미 포인트를 사용한 경우
        //setInflate(ExamInfoType.NOT_INFLATE)
        //2. 시험정보를 받아왔으나 포인트를 사용해야 하는 경우
        setInflate(ExamInfoType.NEED_USE)
        //3. 시험정보가 없을 경우
        setViewType(LectureItemViewType.HIDE_EXAM)
        //setInflate(ExamInfoType.NO_DATA)
        changeData(dummyData)
        changeWriteBtnText(R.string.write_exam)
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