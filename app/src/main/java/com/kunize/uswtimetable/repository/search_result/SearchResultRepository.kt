package com.kunize.uswtimetable.repository.search_result

import com.kunize.uswtimetable.data.remote.LectureMainDto
import retrofit2.Response

class SearchResultRepository(
    private val remoteDataSource: SearchResultRemoteDataSource
) {
    suspend fun getSearchResultList(
        name: String,
        option: String,
        page: Int
    ): Response<LectureMainDto> {
        return remoteDataSource.getSearchResultDataSource(name, option, page)
    }

    suspend fun getLectureMainList(
        option: String,
        page: Int
    ): Response<LectureMainDto> {
        return remoteDataSource.getEvaluationDataSource(option, page)
    }
}