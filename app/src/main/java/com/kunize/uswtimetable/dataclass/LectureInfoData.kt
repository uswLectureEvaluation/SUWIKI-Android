package com.kunize.uswtimetable.dataclass

data class LectureInfoData(
    var lectureType: String = "",
    var yearSemesterList: String = "",
    var infoHoneyScore: Float = 0f,
    var infoLearningScore: Float = 0f,
    var infoSatisfactionScore: Float = 0f,
    var infoMeeting: String = "",
    var infoTask: String = "",
    var infoGrade: String = ""
)
