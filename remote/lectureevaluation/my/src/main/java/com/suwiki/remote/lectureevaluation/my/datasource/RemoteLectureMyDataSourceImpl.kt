package com.suwiki.remote.lectureevaluation.my.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteLectureMyDataSource
import com.suwiki.remote.lectureevaluation.my.api.LectureMyApi
import com.suwiki.remote.lectureevaluation.my.response.toModel
import com.suwiki.core.model.Evaluation
import com.suwiki.core.model.Result
import javax.inject.Inject

class RemoteLectureMyDataSourceImpl @Inject constructor(
    private val lectureApi: LectureMyApi,
) : RemoteLectureMyDataSource {
    override suspend fun getLectureMyPosts(page: Int): com.suwiki.core.model.Result<List<com.suwiki.core.model.Evaluation>> {
        return lectureApi.getEvaluateMyPosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }
}
