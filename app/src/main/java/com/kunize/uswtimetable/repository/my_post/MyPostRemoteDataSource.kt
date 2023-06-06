package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class MyPostRemoteDataSource @Inject constructor(
    private val apiService: IRetrofit,
) : MyPostDataSource {

    override suspend fun getMyEvaluations(page: Int) = apiService.getEvaluatePosts(page)
    override suspend fun getMyExamInfos(page: Int) = apiService.getExamPosts(page)
    override suspend fun deleteMyEvaluation(id: Long) = apiService.deleteEvaluation(id)
    override suspend fun deleteMyExamInfo(id: Long) = apiService.deleteExamInfo(id)
    override suspend fun getPurchaseHistory() = apiService.getPurchaseHistory()
}
