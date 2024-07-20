package com.suwiki.remote.lectureevaluation.response

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import kotlinx.serialization.Serializable

@Serializable
data class LectureEvaluationAverageResponse(
  val id: Long,
  val semesterList: String,
  val professor: String,
  val majorType: String,
  val lectureType: String,
  val lectureName: String,
  val lectureTotalAvg: Float,
  val lectureSatisfactionAvg: Float,
  val lectureHoneyAvg: Float,
  val lectureLearningAvg: Float,
)

internal fun LectureEvaluationAverageResponse.toModel() = LectureEvaluationAverage(
  id = id,
  lectureInfo = LectureInfo(
    semesterList = semesterList.replace(" ", "").split(","),
    professor = professor,
    majorType = majorType,
    lectureType = lectureType,
    lectureName = lectureName,
  ),
  lectureTotalAvg = lectureTotalAvg,
  lectureSatisfactionAvg = lectureSatisfactionAvg,
  lectureHoneyAvg = lectureHoneyAvg,
  lectureLearningAvg = lectureLearningAvg,
)
