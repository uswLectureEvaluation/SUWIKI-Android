package com.suwiki.lectureevaluation.my.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteLectureMyDataSource
import com.suwiki.lectureevaluation.my.api.LectureMyApi
import com.suwiki.lectureevaluation.my.response.toModel
import com.suwiki.model.Evaluation
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteLectureMyDataSourceImpl @Inject constructor(
    private val lectureApi: LectureMyApi,
) : RemoteLectureMyDataSource {
    override suspend fun getLectureMyPosts(page: Int): Result<List<Evaluation>> {
        return lectureApi.getEvaluateMyPosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }
}
