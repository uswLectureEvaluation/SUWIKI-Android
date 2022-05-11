package com.kunize.uswtimetable.repository.evaluation

import com.kunize.uswtimetable.data.remote.LectureMainDto
import retrofit2.Response

interface EvaluationDataSource {
    suspend fun getEvaluationDataSource(option: String, page: Int = 1): Response<LectureMainDto>
}