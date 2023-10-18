package com.suwiki.remote.lectureevaluation.viewer.response.lecture

import com.suwiki.core.model.lectureevaluation.LectureMain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureMainResponse(
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
)

internal fun LectureMainResponse.toModel() = LectureMain(
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
)
