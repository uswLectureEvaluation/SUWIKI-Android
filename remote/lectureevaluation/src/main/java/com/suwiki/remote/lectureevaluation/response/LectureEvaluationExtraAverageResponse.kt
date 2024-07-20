package com.suwiki.remote.lectureevaluation.response

import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

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
  info = LectureInfo(
    semesterList = semesterList.replace(" ", "").split(","),
    professor = professor,
    majorType = majorType,
    lectureType = lectureType,
    lectureName = lectureName,
  ),
  totalAvg = lectureTotalAvg,
  satisfactionAvg = lectureSatisfactionAvg,
  honeyAvg = lectureHoneyAvg,
  learningAvg = lectureLearningAvg,
  teamAvg = TeamLevel.valueOf(lectureTeamAvg.roundToInt()),
  gradeAvg = GradeLevel.valueOf(lectureDifficultyAvg.roundToInt()),
  homeworkAvg = HomeworkLevel.valueOf(lectureHomeworkAvg.roundToInt()),
)
