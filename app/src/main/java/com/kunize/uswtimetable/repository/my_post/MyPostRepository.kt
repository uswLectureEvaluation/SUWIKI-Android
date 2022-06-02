package com.kunize.uswtimetable.repository.my_post

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPostRepository(private val dataSource: MyPostRemoteDataSource) {
    suspend fun getEvaluations(page: Int) = withContext(Dispatchers.IO) { dataSource.getMyEvaluations(page) }
    suspend fun getExamInfos(page: Int) = withContext(Dispatchers.IO){ dataSource.getMyExamInfos(page) }
    suspend fun deleteEvaluation(id: Long) = withContext(Dispatchers.IO) { dataSource.deleteMyEvaluation(id)}
    suspend fun deleteExamInfo(id: Long) = withContext(Dispatchers.IO) { dataSource.deleteMyEvaluation(id)}
    suspend fun getPurchaseHistory() = withContext(Dispatchers.IO) { dataSource.getPurchaseHistory() }
}