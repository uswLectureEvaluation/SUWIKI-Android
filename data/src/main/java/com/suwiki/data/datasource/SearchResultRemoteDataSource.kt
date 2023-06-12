package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result
import javax.inject.Inject

class SearchResultRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) : SearchResultDataSource {

    override suspend fun getEvaluationDataSource(
        option: String,
        page: Int,
        majorType: String,
    ): Result<MutableList<LectureMainDto?>> {
        return apiService.getLectureMainList(option, page, majorType).toResult().map {
            it.data
        }
    }

    override suspend fun getSearchResultDataSource(
        name: String,
        option: String,
        page: Int,
        majorType: String,
    ): Result<MutableList<LectureMainDto?>> {
        return apiService.getSearchResultDetail(name, option, page, majorType).toResult().map {
            it.data
        }
    }
}
