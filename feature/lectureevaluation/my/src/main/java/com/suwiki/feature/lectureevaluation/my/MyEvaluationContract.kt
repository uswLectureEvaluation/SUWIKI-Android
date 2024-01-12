package com.suwiki.feature.lectureevaluation.my

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyEvaluationState(
  val isLoading: Boolean = false,
  val currentPage: Int = 0,
  val currentMyLectureEvaluationListPage: Int = 1,
  val currentMyExamEvaluationListPage: Int = 1,
  val myLectureEvaluationList: PersistentList<MyLectureEvaluation> = persistentListOf(MyLectureEvaluation()),
  val myExamEvaluationList: PersistentList<MyExamEvaluation> = persistentListOf(MyExamEvaluation()),
)

sealed interface MyEvaluationSideEffect {
  data object PopBackStack : MyEvaluationSideEffect
  data class NavigateMyLectureEvaluation(val lectureEvaluation: String) : MyEvaluationSideEffect
  data class NavigateMyExamEvaluation(val examEvaluation: String) : MyEvaluationSideEffect
  data class HandleException(val throwable: Throwable) : MyEvaluationSideEffect
}
