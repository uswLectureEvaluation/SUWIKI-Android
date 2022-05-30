package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import com.kunize.uswtimetable.retrofit.IRetrofit

class MyPostRemoteDataSource(private val apiService: IRetrofit): MyPostDataSource {

    override suspend fun getMyEvaluations(page: Int) = apiService.getEvaluatePosts(page)

    override suspend fun getMyExamInfos(page: Int) = apiService.getExamPosts(page)

    override suspend fun editMyEvaluation(id: Long, request: MyEvaluationEditDto) = apiService.updateEvaluatePost(id, request)

    override suspend fun editMyExamInfo(id: Long, request: MyExamInfoEditDto) = apiService.updateExamPost(id, request)

    override suspend fun deleteMyEvaluation(id: Long) {
        apiService.deleteEvaluation(id)
    }

    override suspend fun deleteMyExamInfo(id: Long) {
        apiService.deleteExamInfo(id)
    }

    override suspend fun getPurchaseHistory() = apiService.getPurchaseHistory()
}