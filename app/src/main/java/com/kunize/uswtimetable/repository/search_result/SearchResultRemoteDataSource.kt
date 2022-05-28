package com.kunize.uswtimetable.repository.search_result

import com.kunize.uswtimetable.data.remote.LectureMainDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.repository.evaluation.EvaluationDataSource
import retrofit2.Response

class SearchResultRemoteDataSource(private val apiService: IRetrofit) : SearchResultDataSource, EvaluationDataSource {
    override suspend fun getSearchResultDataSource(
        name: String,
        option: String,
        page: Int,
        majorType: String
    ): Response<LectureMainDto> {
        return apiService.getSearchResultDetail(name, option, page, majorType)
    }

    override suspend fun getEvaluationDataSource(
        option: String,
        page: Int,
        majorType: String
    ): Response<LectureMainDto> {
        return apiService.getLectureMainList(option, page, majorType)
    }
}