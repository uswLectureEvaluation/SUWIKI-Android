package com.suwiki.feature.lectureevaluation.my

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyEvaluationState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val currentTabPage: Int = 0,
  val myLectureEvaluationList: PersistentList<MyLectureEvaluation> = persistentListOf(),
  val myExamEvaluationList: PersistentList<MyExamEvaluation> = persistentListOf(),
  val showDeleteLectureEvaluationDialog: Boolean = false,
  val showDeleteExamEvaluationDialog: Boolean = false,
  val showLackPointDialog: Boolean = false,
) {
  val showLectureEmptyScreen: Boolean = myLectureEvaluationList.isEmpty()
  val showExamEmptyScreen: Boolean = myExamEvaluationList.isEmpty()
}

sealed interface MyEvaluationSideEffect {
  data object PopBackStack : MyEvaluationSideEffect
  data class NavigateLectureEvaluationEditor(val lectureEvaluation: String) : MyEvaluationSideEffect
  data class NavigateExamEvaluationEditor(val examEvaluation: String) : MyEvaluationSideEffect
  data class HandleException(val throwable: Throwable) : MyEvaluationSideEffect
}
