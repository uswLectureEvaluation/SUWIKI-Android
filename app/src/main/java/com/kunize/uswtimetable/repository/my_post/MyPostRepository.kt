package com.kunize.uswtimetable.repository.my_post

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyPostRepository @Inject constructor(
    private val dataSource: MyPostDataSource,
) {
    suspend fun getEvaluation(page: Int) =
        withContext(Dispatchers.IO) { dataSource.getMyEvaluations(page) }

    suspend fun getExamInfos(page: Int) =
        withContext(Dispatchers.IO) { dataSource.getMyExamInfos(page) }

    suspend fun deleteEvaluation(id: Long) =
        withContext(Dispatchers.IO) { dataSource.deleteMyEvaluation(id) }

    suspend fun deleteExamInfo(id: Long) =
        withContext(Dispatchers.IO) { dataSource.deleteMyExamInfo(id) }

    suspend fun getPurchaseHistory() =
        withContext(Dispatchers.IO) { dataSource.getPurchaseHistory() }
}
