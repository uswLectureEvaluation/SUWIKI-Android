package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluationEditDto
import com.kunize.uswtimetable.dataclass.MyEvaluationListDto
import com.kunize.uswtimetable.dataclass.MyExamInfoEditDto
import com.kunize.uswtimetable.dataclass.MyExamInfoListDto
import retrofit2.Response

interface MyPostDataSource {
    suspend fun getMyEvaluations(): Response<MyEvaluationListDto>
    suspend fun getMyExamInfos(): Response<MyExamInfoListDto>
    suspend fun editMyEvaluation(request: MyEvaluationEditDto)
    suspend fun editMyExamInfo(request: MyExamInfoEditDto)
}