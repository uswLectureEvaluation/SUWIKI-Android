package com.suwiki.feature.myinfo.myevaluation.lectureevaluation

import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel

data class MyLectureEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: List<String> = listOf(""),
  val selectedSemester: String = "",
  val totalAvg: Float = 0f,
  val honeyRating: Float = 0f,
  val learningRating: Float = 0f,
  val satisfactionRating: Float = 0f,
  val gradeLevel: GradeLevel? = null,
  val homeworkLevel: HomeworkLevel? = null,
  val teamLevel: TeamLevel? = null,
  val lectureEvaluation: String = "",
  val showSemesterBottomSheet: Boolean = false,
  val showDeleteLectureEvaluationDialog: Boolean = false,
  val showDeleteLectureEvaluationToastMessage: String = "",
  val showDeleteLectureEvaluationToastVisible: Boolean = false,
)

sealed interface MyLectureEvaluationEditSideEffect {
  data object PopBackStack : MyLectureEvaluationEditSideEffect
  data object ShowMyLectureEvaluationDeleteToast : MyLectureEvaluationEditSideEffect
  data object ShowMyLectureEvaluationReviseToast : MyLectureEvaluationEditSideEffect
  data class HandleException(val throwable: Throwable) : MyLectureEvaluationEditSideEffect
}
