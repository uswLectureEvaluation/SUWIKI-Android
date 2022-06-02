package com.kunize.uswtimetable.data.remote

import com.google.gson.annotations.SerializedName

data class LectureMain(
    val id: Long,
    @SerializedName("semesterList") val semester: String,
    val professor: String,
    val majorType: String,
    val lectureType: String,
    val lectureName: String,
    val lectureTotalAvg: Float,
    val lectureSatisfactionAvg: Float,
    val lectureHoneyAvg: Float,
    val lectureLearningAvg: Float
)