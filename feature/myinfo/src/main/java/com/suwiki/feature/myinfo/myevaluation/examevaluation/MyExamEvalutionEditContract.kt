package com.suwiki.feature.myinfo.myevaluation.examevaluation

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyExamEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: PersistentList<String> = persistentListOf(""),
  val selectedSemester: String? = null,
  val selectedExamType: String? = null,
  val selectedSemesterPosition: Int? = null,
  val selectedExamTypePosition: Int? = null,
  val examEvaluation: String = "",
  val examLevel: String = "",
  val examInfo: PersistentList<String> = persistentListOf(""),
  val showSemesterBottomSheet: Boolean = false,
  val showExamTypeBottomSheet: Boolean = false,
  val showDeleteExamEvaluationDialog: Boolean = false,
)

sealed interface MyExamEvaluationEditSideEffect {
  data object PopBackStack : MyExamEvaluationEditSideEffect
  data object ShowMyExamEvaluationDeleteToast : MyExamEvaluationEditSideEffect
  data object ShowMyExamEvaluationReviseToast : MyExamEvaluationEditSideEffect
  data class HandleException(val throwable: Throwable) : MyExamEvaluationEditSideEffect
}
