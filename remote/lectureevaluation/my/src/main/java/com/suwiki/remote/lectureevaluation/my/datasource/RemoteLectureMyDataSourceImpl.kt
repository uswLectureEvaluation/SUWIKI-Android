package com.suwiki.remote.lectureevaluation.my.datasource

import com.suwiki.core.model.lectureevaluation.Evaluation
import com.suwiki.data.datasource.remote.RemoteLectureMyDataSource
import com.suwiki.remote.lectureevaluation.my.api.LectureMyApi
import com.suwiki.remote.lectureevaluation.my.response.toModel
import javax.inject.Inject

class RemoteLectureMyDataSourceImpl @Inject constructor(
    private val lectureApi: LectureMyApi,
) : RemoteLectureMyDataSource {
    override suspend fun getLectureMyPosts(page: Int): List<Evaluation> {
        return lectureApi.getEvaluateMyPosts(page).getOrThrow().data.map { it.toModel() }
    }
}
