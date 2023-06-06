package com.kunize.uswtimetable.repository.search_result

import javax.inject.Inject

class SearchResultRepository @Inject constructor(
    private val remoteDataSource: SearchResultDataSource,
) {
    suspend fun getSearchResultList(
        name: String,
        option: String,
        page: Int,
        majorType: String,
    ) = remoteDataSource.getSearchResultDataSource(name, option, page, majorType)

    suspend fun getLectureMainList(
        option: String,
        page: Int,
        majorType: String = "",
    ) = remoteDataSource.getEvaluationDataSource(option, page, majorType)
}
