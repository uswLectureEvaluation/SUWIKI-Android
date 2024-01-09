package com.suwiki.feature.myinfo.myevaluation.examevaluation

data class MyExamEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val selectedSemester: String = "",
  val selectedExamType: String = "",
  val examEvaluation: String = "",
  val examDifficulty: String = "",
  val examInfo: String = "",
  val examType: String? = null,
  val showSemesterBottomSheet: Boolean = false,
  val showExamTypeBottomSheet: Boolean = false,
  val showDeleteExamEvaluationDialog: Boolean = false,
  val showDeleteExamEvaluationToastMessage: String = "",
  val showDeleteExamEvaluationToastVisible: Boolean = false,
)

sealed interface MyExamEvaluationEditSideEffect {
  data object PopBackStack : MyExamEvaluationEditSideEffect
  data object ShowMyExamEvaluationDeleteToast : MyExamEvaluationEditSideEffect
  data object ShowMyExamEvaluationReviseToast : MyExamEvaluationEditSideEffect
}
