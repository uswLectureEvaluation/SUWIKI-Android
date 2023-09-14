package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteLectureMyDataSource
import com.suwiki.model.Evaluation
import com.suwiki.model.Result
import com.suwiki.remote.api.LectureApi
import com.suwiki.remote.response.evaluation.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteLectureMyDataSourceImpl @Inject constructor(
    private val lectureApi: LectureApi,
) : RemoteLectureMyDataSource {
    override suspend fun getLectureMyPosts(page: Int): Result<List<Evaluation>> {
        return lectureApi.getEvaluateMyPosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }
}
