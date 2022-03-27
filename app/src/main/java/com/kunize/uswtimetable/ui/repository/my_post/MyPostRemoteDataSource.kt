package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import com.kunize.uswtimetable.retrofit.IRetrofit

class MyPostRemoteDataSource: MyPostDataSource {
    private val apiService = IRetrofit.getInstance()

    override suspend fun getMyEvaluations() = apiService.getEvaluatePosts(1)

    override suspend fun getMyExamInfos() = apiService.getExamPosts()

    override suspend fun editMyEvaluation(request: MyEvaluationEditDto) = apiService.updateEvaluatePost(request)

    override suspend fun editMyExamInfo(request: MyExamInfoEditDto) = apiService.updateExamPost(request)
}