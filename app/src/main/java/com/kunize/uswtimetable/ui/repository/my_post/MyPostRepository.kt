package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPostRepository(private val dataSource: MyPostRemoteDataSource) {
    suspend fun getEvaluations() = withContext(Dispatchers.IO) { dataSource.getMyEvaluations() }
    suspend fun getExamInfos() = withContext(Dispatchers.IO){ dataSource.getMyExamInfos() }
    suspend fun editEvaluation(request: MyEvaluationEditDto) =
        withContext(Dispatchers.IO){ dataSource.editMyEvaluation(request) }
    suspend fun editExamInfo(request: MyExamInfoEditDto) =
        withContext(Dispatchers.IO) { dataSource.editMyExamInfo(request) }
}