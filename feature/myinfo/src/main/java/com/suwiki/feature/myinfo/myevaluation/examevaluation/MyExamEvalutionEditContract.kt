package com.suwiki.feature.myinfo.myevaluation.examevaluation

data class MyExamEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val selectedSemester: String = "",
  val selectedExamType: String = "",
  val examEvalution: String = "",
  val examDifficulty: String = "",
  val examInfo: String = "",
  val examType: String? = null,
  val showSemesterBottomSheet: Boolean = false,
  val showExamTypeBottomSheet: Boolean = false,
  val showDeleteExamEvalutionDialog: Boolean = false,
  val showDeleteExamEvalutionToastMessage: String = "",
  val showDeleteExamEvalutionToastVisible: Boolean = false,
)

sealed interface MyExamEvalutionEditSideEffect {
  data object PopBackStack : MyExamEvalutionEditSideEffect
}
