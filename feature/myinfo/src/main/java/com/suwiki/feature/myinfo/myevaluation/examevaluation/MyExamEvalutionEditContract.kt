package com.suwiki.feature.myinfo.myevaluation.examevaluation

import com.suwiki.core.model.enums.ExamLevel
import com.suwiki.core.model.enums.ExamType

data class MyExamEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: List<String> = listOf(""),
  val selectedSemester: String = "",
  val selectedExamInfo: String = "",
  val examEvaluation: String = "",
  val examLevel: ExamLevel? = null,
  val examInfo: String = "",
  val examType: ExamType? = null,
  val showSemesterBottomSheet: Boolean = false,
  val showExamInfoBottomSheet: Boolean = false,
  val showDeleteExamEvaluationDialog: Boolean = false,
  val showDeleteExamEvaluationToastMessage: String = "",
  val showDeleteExamEvaluationToastVisible: Boolean = false,
)

sealed interface MyExamEvaluationEditSideEffect {
  data object PopBackStack : MyExamEvaluationEditSideEffect
  data object ShowMyExamEvaluationDeleteToast : MyExamEvaluationEditSideEffect
  data object ShowMyExamEvaluationReviseToast : MyExamEvaluationEditSideEffect
  data class HandleException(val throwable: Throwable) : MyExamEvaluationEditSideEffect
}
