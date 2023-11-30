package com.suwiki.data.lectureevaluation.my.datasource

import com.suwiki.core.model.lectureevaluation.PurchaseHistory
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation

interface RemoteExamMyDataSource {

  suspend fun getMyExamEvaluationList(page: Int): List<MyExamEvaluation>

  suspend fun getPurchaseHistory(): List<PurchaseHistory>
}
