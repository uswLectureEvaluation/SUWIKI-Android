package com.kunize.uswtimetable.repository.search_result

class SearchResultRepository(
    private val remoteDataSource: SearchResultRemoteDataSource
) {
    suspend fun getSearchResultList(
        name: String,
        option: String,
        page: Int,
        majorType: String
    ) = remoteDataSource.getSearchResultDataSource(name, option, page, majorType)

    suspend fun getLectureMainList(
        option: String,
        page: Int,
        majorType: String = ""
    ) = remoteDataSource.getEvaluationDataSource(option, page, majorType)
}