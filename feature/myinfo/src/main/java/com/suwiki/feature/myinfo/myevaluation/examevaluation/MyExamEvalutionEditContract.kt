package com.suwiki.feature.myinfo.myevaluation.examevaluation

import com.suwiki.core.model.enums.ExamInfo
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyExamEvaluationEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: List<String> = listOf(""),
  val selectedSemester: String? = null,
  val selectedExamType: String? = null,
  val examEvaluation: String = "",
  val examLevel: String? = null,
  val examInfo: PersistentList<String> = persistentListOf(""),
  val examType: ExamInfo? = null,
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
  data class HandleException(val throwable: Throwable) : MyExamEvaluationEditSideEffect
}
