package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.toResult
import com.suwiki.domain.di.AuthApiService
import javax.inject.Inject

class MyPostRemoteDataSource @Inject constructor(
    @AuthApiService private val apiService: ApiService,
) : MyPostDataSource {

    override suspend fun getMyEvaluations(page: Int) =
        apiService.getEvaluatePosts(page).toResult().map { it.data }

    override suspend fun getMyExamInfos(page: Int) =
        apiService.getExamPosts(page).toResult().map { it.data }

    override suspend fun deleteMyEvaluation(id: Long) = apiService.deleteEvaluation(id)
    override suspend fun deleteMyExamInfo(id: Long) = apiService.deleteExamInfo(id)
    override suspend fun getPurchaseHistory() =
        apiService.getPurchaseHistory().toResult().map { it.data }
}
