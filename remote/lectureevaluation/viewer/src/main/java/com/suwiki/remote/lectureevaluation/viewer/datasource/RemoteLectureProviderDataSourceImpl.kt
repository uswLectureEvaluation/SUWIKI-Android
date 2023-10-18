package com.suwiki.remote.lectureevaluation.viewer.datasource

import com.suwiki.core.model.LectureDetailEvaluationData
import com.suwiki.core.model.LectureDetailInfo
import com.suwiki.core.model.LectureMain
import com.suwiki.data.datasource.remote.RemoteLectureProviderDataSource
import com.suwiki.remote.lectureevaluation.viewer.api.LectureViewerApi
import com.suwiki.remote.lectureevaluation.viewer.response.lecture.toModel
import javax.inject.Inject

class RemoteLectureProviderDataSourceImpl @Inject constructor(
    private val lectureApi: LectureViewerApi,
) : RemoteLectureProviderDataSource {

    override suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): LectureDetailEvaluationData {
        return lectureApi.getLectureDetailEvaluation(lectureId = lectureId, page = page)
            .getOrThrow().toModel()
    }

    override suspend fun getLectureMainList(
        option: String,
        page: Int,
        majorType: String,
    ): List<LectureMain?> {
        return lectureApi.getLectureMainList(
            option = option,
            page = page,
            majorType = majorType,
        ).getOrThrow().data.map { it?.toModel() }
    }

    override suspend fun getSearchResultDetail(
        searchValue: String,
        option: String,
        page: Int,
        majorType: String,
    ): List<LectureMain?> {
        return lectureApi.getSearchResultDetail(
            searchValue = searchValue,
            option = option,
            page = page,
            majorType = majorType,
        ).getOrThrow().data.map { it?.toModel() }
    }

    override suspend fun getLectureDetailInfo(lectureId: Long): LectureDetailInfo {
        return lectureApi.getLectureDetailInfo(lectureId).getOrThrow().data.toModel()
    }
}
