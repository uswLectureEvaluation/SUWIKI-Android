package com.kunize.uswtimetable.repository.evaluation

import com.kunize.uswtimetable.retrofit.IRetrofit

class EvaluationRemoteDataSource(private val apiService: IRetrofit) : EvaluationDataSource {

    override suspend fun getEvaluationDataSource(
        option: String,
        page: Int,
        majorType: String
    ) = apiService.getLectureMainList(option, majorType = majorType)
}