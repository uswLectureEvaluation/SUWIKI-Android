package com.suwiki.data.lectureevaluation.repository

import com.suwiki.common.model.lectureevaluation.PurchaseHistory
import com.suwiki.common.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.data.lectureevaluation.datasource.RemoteExamMyDataSource
import com.suwiki.domain.lectureevaluation.repository.ExamMyRepository
import javax.inject.Inject

class ExamMyRepositoryImpl @Inject constructor(
  private val remoteExamMyDataSource: RemoteExamMyDataSource,
) : ExamMyRepository {
  override suspend fun getMyExamEvaluationList(page: Int): List<MyExamEvaluation> {
    return remoteExamMyDataSource.getMyExamEvaluationList(page = page)
  }

  override suspend fun getPurchaseHistory(): List<PurchaseHistory> {
    return remoteExamMyDataSource.getPurchaseHistory()
  }
}
