package com.kunize.uswtimetable.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LectureDetailInfoDto(
    val data: LectureDetailInfoDataDto
)

data class LectureDetailInfoDataDto(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val professor: String,
    val majorType: String,
    val lectureType: String,
    val lectureName: String,
    val lectureTotalAvg: Float,
    val lectureSatisfactionAvg: Float,
    val lectureHoneyAvg: Float,
    val lectureLearningAvg: Float,
    val lectureTeamAvg: Float,
    val lectureDifficultyAvg: Float,
    val lectureHomeworkAvg: Float
) : Serializable
