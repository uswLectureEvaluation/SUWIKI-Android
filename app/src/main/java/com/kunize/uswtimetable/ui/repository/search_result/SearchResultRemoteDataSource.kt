package com.kunize.uswtimetable.ui.repository.search_result

import com.kunize.uswtimetable.dataclass.LectureMainDto
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.repository.evaluation.EvaluationDataSource
import retrofit2.Response

class SearchResultRemoteDataSource() : SearchResultDataSource, EvaluationDataSource {
    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }
    override suspend fun getSearchResultDataSource(
        name: String,
        option: String,
        page: Int
    ): Response<LectureMainDto> {
        return retrofit.getSearchResultDetail(name, option, page)
    }

    override suspend fun getEvaluationDataSource(
        option: String,
        page: Int
    ): Response<LectureMainDto> {
        return retrofit.getLectureMainList(option, page)
    }
}