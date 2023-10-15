package com.suwiki.lectureevaluation.viewer.response.lecture

import com.suwiki.model.LectureDetailInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureDetailInfoResponse(
    val id: Long,
    @SerialName("semesterList") val semester: String,
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
    val lectureHomeworkAvg: Float,
)

internal fun LectureDetailInfoResponse.toModel() = LectureDetailInfo(
    id = id,
    semester = semester,
    professor = professor,
    majorType = majorType,
    lectureType = lectureType,
    lectureName = lectureName,
    lectureTotalAvg = lectureTotalAvg,
    lectureSatisfactionAvg = lectureSatisfactionAvg,
    lectureHoneyAvg = lectureHoneyAvg,
    lectureLearningAvg = lectureLearningAvg,
    lectureTeamAvg = lectureTeamAvg,
    lectureDifficultyAvg = lectureDifficultyAvg,
    lectureHomeworkAvg = lectureHomeworkAvg,
)
