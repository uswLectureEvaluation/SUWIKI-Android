package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteEvaluateProviderDataSource
import com.suwiki.model.Evaluation
import com.suwiki.model.LectureDetailEvaluationData
import com.suwiki.model.Result
import com.suwiki.remote.api.EvaluateApi
import com.suwiki.remote.response.evaluation.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteEvaluateProviderDataSourceImpl @Inject constructor(
    private val evaluateApi: EvaluateApi,
) : RemoteEvaluateProviderDataSource {
    override suspend fun getEvaluatePosts(page: Int): Result<List<Evaluation>> {
        return evaluateApi.getEvaluatePosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData> {
        return evaluateApi.getLectureDetailEvaluation(lectureId = lectureId, page = page).toResult()
            .map {
                it.toModel()
            }
    }
}
