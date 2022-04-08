package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyEvaluationListDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import com.kunize.uswtimetable.dataclass.MyExamListDto
import retrofit2.Response

interface MyPostDataSource {
    suspend fun getMyEvaluations(page: Int): Response<MyEvaluationListDto>
    suspend fun getMyExamInfos(page: Int): Response<MyExamListDto>
    suspend fun editMyEvaluation(request: MyEvaluationEditDto)
    suspend fun editMyExamInfo(request: MyExamInfoEditDto)
}