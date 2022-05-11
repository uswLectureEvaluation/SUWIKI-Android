package com.kunize.uswtimetable.repository.evaluation

import com.kunize.uswtimetable.data.remote.LectureMainDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class EvaluationRemoteDataSource(private val apiService: IRetrofit): EvaluationDataSource {

    override suspend fun getEvaluationDataSource(option: String, page: Int): Response<LectureMainDto> {
        return apiService.getLectureMainList(option)
    }
}