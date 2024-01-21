package com.suwiki.remote.lectureevaluation.viewerreporter.response.lecture

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import kotlinx.serialization.Serializable

@Serializable
data class LectureEvaluationExtraAverageResponse(
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
  val lectureTeamAvg: Float,
  val lectureDifficultyAvg: Float,
  val lectureHomeworkAvg: Float,
)

internal fun LectureEvaluationExtraAverageResponse.toModel() = LectureEvaluationExtraAverage(
  id = id,
  lectureInfo = LectureInfo(
    semesterList = semesterList.replace(" ","").split(","),
    professor = professor,
    majorType = majorType,
    lectureType = lectureType,
    lectureName = lectureName,
  ),
  lectureTotalAvg = lectureTotalAvg,
  lectureSatisfactionAvg = lectureSatisfactionAvg,
  lectureHoneyAvg = lectureHoneyAvg,
  lectureLearningAvg = lectureLearningAvg,
  lectureTeamAvg = lectureTeamAvg,
  lectureDifficultyAvg = lectureDifficultyAvg,
  lectureHomeworkAvg = lectureHomeworkAvg,
)
