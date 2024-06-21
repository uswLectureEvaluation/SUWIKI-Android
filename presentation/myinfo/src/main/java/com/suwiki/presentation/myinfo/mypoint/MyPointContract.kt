package com.suwiki.presentation.myinfo.mypoint

import com.suwiki.core.model.lectureevaluation.PurchaseHistory
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyPointState(
  val isLoading: Boolean = false,
  val currentPoint: Int = 0,
  val writtenLectureEvaluations: Int = 0,
  val writtenExamEvaluations: Int = 0,
  val viewExam: Int = 0,
  val purchaseHistory: PersistentList<PurchaseHistory> = persistentListOf(),
)

sealed interface MyPointSideEffect {
  data object PopBackStack : MyPointSideEffect
  data class HandleException(val throwable: Throwable) : MyPointSideEffect
}
