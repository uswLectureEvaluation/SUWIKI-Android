package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPostRepository(private val dataSource: MyPostRemoteDataSource) {
    suspend fun getEvaluations(page: Int) = withContext(Dispatchers.IO) { dataSource.getMyEvaluations(page) }
    suspend fun getExamInfos(page: Int) = withContext(Dispatchers.IO){ dataSource.getMyExamInfos(page) }
    suspend fun editEvaluation(id: Long, request: MyEvaluationEditDto) =
        withContext(Dispatchers.IO){ dataSource.editMyEvaluation(id, request) }
    suspend fun editExamInfo(id: Long, request: MyExamInfoEditDto) =
        withContext(Dispatchers.IO) { dataSource.editMyExamInfo(id, request) }
    suspend fun deleteEvaluation(id: Long) = withContext(Dispatchers.IO) { dataSource.deleteMyEvaluation(id)}
    suspend fun deleteExamInfo(id: Long) = withContext(Dispatchers.IO) { dataSource.deleteMyEvaluation(id)}
    suspend fun getPurchaseHistory() = withContext(Dispatchers.IO) { dataSource.getPurchaseHistory() }
}