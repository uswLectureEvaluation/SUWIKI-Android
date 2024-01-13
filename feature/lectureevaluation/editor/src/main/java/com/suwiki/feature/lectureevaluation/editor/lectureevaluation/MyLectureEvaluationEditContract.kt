package com.suwiki.feature.lectureevaluation.editor.lectureevaluation

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyLectureEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: PersistentList<String> = persistentListOf(""),
  val selectedSemester: String = "",
  val selectedSemesterPosition: Int? = null,
  val totalAvg: Float = 0f,
  val honeyRating: Float = 0f,
  val learningRating: Float = 0f,
  val satisfactionRating: Float = 0f,
  val gradeLevel: Int = 0,
  val homeworkLevel: Int = 0,
  val teamLevel: Int = 0,
  val lectureEvaluation: String = "",
  val showSemesterBottomSheet: Boolean = false,
  val showDeleteLectureEvaluationDialog: Boolean = false,
  val showLackPointDialog: Boolean = false,
)

sealed interface MyLectureEvaluationEditSideEffect {
  data object PopBackStack : MyLectureEvaluationEditSideEffect
  data object ShowMyLectureEvaluationDeleteToast : MyLectureEvaluationEditSideEffect
  data object ShowMyLectureEvaluationReviseToast : MyLectureEvaluationEditSideEffect
  data class HandleException(val throwable: Throwable) : MyLectureEvaluationEditSideEffect
}
