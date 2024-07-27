package com.suwiki.presentation.lectureevaluation.examevaluation

import com.suwiki.common.model.enums.ExamLevel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ExamEvaluationEditorState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: PersistentList<String> = persistentListOf(""),
  val selectedSemester: String? = null,
  val selectedExamType: String? = null,
  val selectedSemesterPosition: Int? = null,
  val selectedExamTypePosition: Int? = null,
  val examEvaluation: String = "",
  val examLevel: ExamLevel? = null,
  val examInfo: PersistentList<String> = persistentListOf(),
  val showSemesterBottomSheet: Boolean = false,
  val showExamTypeBottomSheet: Boolean = false,
) {
  val buttonEnabled = examLevel != null &&
    examInfo.isNotEmpty() &&
    examEvaluation.isNotEmpty()
}

sealed interface ExamEvaluationEditorSideEffect {
  data object ShowInputMoreTextToast : ExamEvaluationEditorSideEffect
  data object ShowSelectSemesterToast : ExamEvaluationEditorSideEffect
  data object ShowSelectExamTypeToast : ExamEvaluationEditorSideEffect
  data object PopBackStack : ExamEvaluationEditorSideEffect
  data object ShowExamEvaluationDeleteToast : ExamEvaluationEditorSideEffect
  data class HandleException(val throwable: Throwable) : ExamEvaluationEditorSideEffect
}
