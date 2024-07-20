package com.suwiki.domain.lectureevaluation.repository

import com.suwiki.core.model.lectureevaluation.PurchaseHistory
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation

interface ExamMyRepository {

  suspend fun getMyExamEvaluationList(page: Int): List<MyExamEvaluation>

  suspend fun getPurchaseHistory(): List<PurchaseHistory>
}
