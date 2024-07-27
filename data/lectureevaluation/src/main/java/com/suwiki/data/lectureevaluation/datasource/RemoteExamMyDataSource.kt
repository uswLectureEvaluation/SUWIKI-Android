package com.suwiki.data.lectureevaluation.datasource

import com.suwiki.common.model.lectureevaluation.PurchaseHistory
import com.suwiki.common.model.lectureevaluation.exam.MyExamEvaluation

interface RemoteExamMyDataSource {

  suspend fun getMyExamEvaluationList(page: Int): List<MyExamEvaluation>

  suspend fun getPurchaseHistory(): List<PurchaseHistory>
}
