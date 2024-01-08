package com.suwiki.feature.myinfo.myevaluation

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyEvaluationState(
  val isLoading: Boolean = false,
  val currentPage: Int = 0,
  val myLectureEvaluationList: PersistentList<MyLectureEvaluation> = persistentListOf(MyLectureEvaluation()),
  val myExamEvaluationList: PersistentList<MyExamEvaluation> = persistentListOf(MyExamEvaluation()),
)

sealed interface MyEvaluationSideEffect {
  data object PopBackStack : MyEvaluationSideEffect
  data object NavigateMyLectureEvaluation : MyEvaluationSideEffect
  data object NavigateMyExamEvaluation : MyEvaluationSideEffect
}
