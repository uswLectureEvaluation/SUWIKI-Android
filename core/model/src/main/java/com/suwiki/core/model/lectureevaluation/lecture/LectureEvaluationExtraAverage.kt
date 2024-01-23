package com.suwiki.core.model.lectureevaluation.lecture

import androidx.compose.runtime.Stable
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel

@Stable
data class LectureEvaluationExtraAverage(
  val id: Long = 0,
  val info: LectureInfo = LectureInfo(),
  val totalAvg: Float = 0f,
  val satisfactionAvg: Float = 0f,
  val honeyAvg: Float = 0f,
  val learningAvg: Float = 0f,
  val teamAvg: TeamLevel = TeamLevel.EXIST,
  val gradeAvg: GradeLevel = GradeLevel.DIFFICULT,
  val homeworkAvg: HomeworkLevel = HomeworkLevel.MANY,
)
