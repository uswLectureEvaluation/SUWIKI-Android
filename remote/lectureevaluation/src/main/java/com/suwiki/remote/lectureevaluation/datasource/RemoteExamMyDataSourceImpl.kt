package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.core.model.lectureevaluation.PurchaseHistory
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.data.lectureevaluation.datasource.RemoteExamMyDataSource
import com.suwiki.remote.lectureevaluation.api.ExamMyApi
import com.suwiki.remote.lectureevaluation.response.toModel
import javax.inject.Inject

class RemoteExamMyDataSourceImpl @Inject constructor(
  private val examApi: ExamMyApi,
) : RemoteExamMyDataSource {

  override suspend fun getMyExamEvaluationList(page: Int): List<MyExamEvaluation> {
    return examApi.getMyExamEvaluationList(page).getOrThrow().data.map { it.toModel() }
  }

  override suspend fun getPurchaseHistory(): List<PurchaseHistory> {
    return examApi.getPurchaseHistory().getOrThrow().data.map { it.toModel() }
  }
}
