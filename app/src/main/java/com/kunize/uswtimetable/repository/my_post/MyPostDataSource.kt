package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.data.remote.DataDto
import com.kunize.uswtimetable.data.remote.LectureExamDto
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.dataclass.PurchaseHistory
import retrofit2.Response

interface MyPostDataSource {
    suspend fun getMyEvaluations(page: Int): DataDto<List<MyEvaluationDto>>
    suspend fun getMyExamInfos(page: Int): Response<DataDto<List<LectureExamDto>>>
    suspend fun deleteMyEvaluation(id: Long)
    suspend fun deleteMyExamInfo(id: Long)
    suspend fun getPurchaseHistory(): Response<DataDto<List<PurchaseHistory>>>
}