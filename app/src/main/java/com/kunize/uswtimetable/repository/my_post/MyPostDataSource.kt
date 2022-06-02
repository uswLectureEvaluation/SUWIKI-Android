package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationListDto
import com.kunize.uswtimetable.dataclass.MyExamInfoListDto
import com.kunize.uswtimetable.dataclass.PurchaseHistoryDto
import retrofit2.Response

interface MyPostDataSource {
    suspend fun getMyEvaluations(page: Int): Response<MyEvaluationListDto>
    suspend fun getMyExamInfos(page: Int): Response<MyExamInfoListDto>
    suspend fun deleteMyEvaluation(id: Long)
    suspend fun deleteMyExamInfo(id: Long)
    suspend fun getPurchaseHistory(): Response<PurchaseHistoryDto>
}