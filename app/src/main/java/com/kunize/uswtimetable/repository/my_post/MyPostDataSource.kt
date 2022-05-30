package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.dataclass.*
import retrofit2.Response

interface MyPostDataSource {
    suspend fun getMyEvaluations(page: Int): Response<MyEvaluationListDto>
    suspend fun getMyExamInfos(page: Int): Response<MyExamInfoListDto>
    suspend fun editMyEvaluation(id: Long, request: MyEvaluationEditDto)
    suspend fun editMyExamInfo(id: Long, request: MyExamInfoEditDto)
    suspend fun deleteMyEvaluation(id: Long)
    suspend fun deleteMyExamInfo(id: Long)
    suspend fun getPurchaseHistory(): Response<PurchaseHistoryDto>
}