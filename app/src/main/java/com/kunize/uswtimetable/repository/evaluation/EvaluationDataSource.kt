package com.kunize.uswtimetable.repository.evaluation

import com.kunize.uswtimetable.data.remote.DataDto
import com.kunize.uswtimetable.data.remote.LectureMain
import retrofit2.Response

interface EvaluationDataSource {
    suspend fun getEvaluationDataSource(option: String, page: Int = 1, majorType: String): Response<DataDto<MutableList<LectureMain?>>>
}