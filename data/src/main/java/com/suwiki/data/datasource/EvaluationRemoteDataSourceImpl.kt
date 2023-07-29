package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.data.network.toDomainResult
import com.suwiki.domain.model.Result
import javax.inject.Inject

class EvaluationRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : EvaluationDataSource {

    override suspend fun getEvaluationDataSource(
        option: String,
        page: Int,
        majorType: String,
    ): Result<MutableList<LectureMainDto?>> {
        return apiService.getLectureMainList(option, majorType = majorType).toDomainResult().map {
            it.data
        }
    }
}
