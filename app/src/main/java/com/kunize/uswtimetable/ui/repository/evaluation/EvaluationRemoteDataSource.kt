package com.kunize.uswtimetable.ui.repository.evaluation

import com.kunize.uswtimetable.dataclass.LectureMainDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class EvaluationRemoteDataSource(private val apiService: IRetrofit): EvaluationDataSource {

    override suspend fun getEvaluationDataSource(option: String, page: Int): Response<LectureMainDto> {
        return apiService.getLectureMainList(option)
    }
}