package com.suwiki.feature.myinfo.myreview

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyReviewState(
  val isLoading: Boolean = false,
  val currentPage: Int = 0,
  val point: Int = 0,
  val myLectureEvaluationList: PersistentList<MyLectureEvaluation> = persistentListOf(MyLectureEvaluation()),
  val myExamEvaluationList: PersistentList<MyExamEvaluation> = persistentListOf(MyExamEvaluation()),
)

sealed interface MyReviewSideEffect {
  data object PopBackStack : MyReviewSideEffect
  data class NavigateMyClassReview(val point: Int) : MyReviewSideEffect
  data class NavigateMyTestReview(val point: Int) : MyReviewSideEffect
}
