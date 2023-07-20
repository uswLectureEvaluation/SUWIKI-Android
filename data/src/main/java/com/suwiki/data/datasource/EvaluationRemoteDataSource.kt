package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result
import javax.inject.Inject

class EvaluationRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) : EvaluationDataSource {

    override suspend fun getEvaluationDataSource(
        option: String,
        page: Int,
        majorType: String,
    ): Result<MutableList<LectureMainDto?>> {
        return apiService.getLectureMainList(option, majorType = majorType).toResult().map {
            it.data
        }
    }
}
