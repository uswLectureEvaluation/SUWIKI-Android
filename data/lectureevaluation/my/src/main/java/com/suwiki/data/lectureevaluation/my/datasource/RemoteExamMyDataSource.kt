package com.suwiki.data.lectureevaluation.my.datasource

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.PurchaseHistory

interface RemoteExamMyDataSource {

  suspend fun getMyExamEvaluationList(page: Int): List<MyExamEvaluation>

  suspend fun getPurchaseHistory(): List<PurchaseHistory>
}
