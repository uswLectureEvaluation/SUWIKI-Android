package com.suwiki.core.model.lectureevaluation

data class LectureMain(
    val id: Long,
    val semester: String,
    val professor: String,
    val majorType: String,
    val lectureType: String,
    val lectureName: String,
    val lectureTotalAvg: Float,
    val lectureSatisfactionAvg: Float,
    val lectureHoneyAvg: Float,
    val lectureLearningAvg: Float,
)
