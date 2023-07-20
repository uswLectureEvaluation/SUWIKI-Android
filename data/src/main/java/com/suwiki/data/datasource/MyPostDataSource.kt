package com.suwiki.data.datasource

import com.suwiki.data.network.dto.LectureExamDto
import com.suwiki.data.network.dto.MyEvaluationDto
import com.suwiki.data.network.dto.PurchaseHistoryDto
import com.suwiki.domain.model.Result

interface MyPostDataSource {
    suspend fun getMyEvaluations(page: Int): Result<List<MyEvaluationDto>>
    suspend fun getMyExamInfos(page: Int): Result<List<LectureExamDto>>
    suspend fun deleteMyEvaluation(id: Long)
    suspend fun deleteMyExamInfo(id: Long)
    suspend fun getPurchaseHistory(): Result<List<PurchaseHistoryDto>>
}
