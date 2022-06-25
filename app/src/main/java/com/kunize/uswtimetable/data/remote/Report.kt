package com.kunize.uswtimetable.data.remote

data class ReportLecture(
    val evaluateIdx: Long,
    val content: String = ""
)

data class ReportExam(
    val examIdx: Long,
    val content: String = ""
)
