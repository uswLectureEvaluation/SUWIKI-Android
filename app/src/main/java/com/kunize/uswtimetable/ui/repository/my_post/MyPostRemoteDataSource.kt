package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import com.kunize.uswtimetable.retrofit.IRetrofit

class MyPostRemoteDataSource: MyPostDataSource {
    private val apiService = IRetrofit.getInstance()

    override suspend fun getMyEvaluations(page: Int) = apiService.getEvaluatePosts(page)

    override suspend fun getMyExamInfos(page: Int) = apiService.getExamPosts(page)

    override suspend fun editMyEvaluation(request: MyEvaluationEditDto) = apiService.updateEvaluatePost(request)

    override suspend fun editMyExamInfo(request: MyExamInfoEditDto) = apiService.updateExamPost(request)

    override suspend fun deleteMyEvaluation(id: Long) {
        apiService.deleteEvaluation(id)
    }

    override suspend fun deleteMyExamInfo(id: Long) {
        apiService.deleteExamInfo(id)
    }

    override suspend fun getPurchaseHistory() = apiService.getPurchaseHistory()
}