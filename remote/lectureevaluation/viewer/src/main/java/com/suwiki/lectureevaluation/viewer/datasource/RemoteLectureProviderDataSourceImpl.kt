package com.suwiki.lectureevaluation.viewer.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteLectureProviderDataSource
import com.suwiki.lectureevaluation.viewer.api.LectureViewerApi
import com.suwiki.lectureevaluation.viewer.response.lecture.toModel
import com.suwiki.model.LectureDetailEvaluationData
import com.suwiki.model.LectureDetailInfo
import com.suwiki.model.LectureMain
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteLectureProviderDataSourceImpl @Inject constructor(
    private val lectureApi: LectureViewerApi,
) : RemoteLectureProviderDataSource {

    override suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData> {
        return lectureApi.getLectureDetailEvaluation(lectureId = lectureId, page = page).toResult()
            .map {
                it.toModel()
            }
    }

    override suspend fun getLectureMainList(
        option: String,
        page: Int,
        majorType: String,
    ): Result<List<LectureMain?>> {
        return lectureApi.getLectureMainList(
            option = option,
            page = page,
            majorType = majorType,
        ).toResult().map { result -> result.data.map { it?.toModel() } }
    }

    override suspend fun getSearchResultDetail(
        searchValue: String,
        option: String,
        page: Int,
        majorType: String,
    ): Result<List<LectureMain?>> {
        return lectureApi.getSearchResultDetail(
            searchValue = searchValue,
            option = option,
            page = page,
            majorType = majorType,
        ).toResult().map { result -> result.data.map { it?.toModel() } }
    }

    override suspend fun getLectureDetailInfo(lectureId: Long): Result<LectureDetailInfo> {
        return lectureApi.getLectureDetailInfo(lectureId).toResult().map { it.data.toModel() }
    }
}
