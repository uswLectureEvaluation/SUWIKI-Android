package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteLectureDataSource
import com.suwiki.model.LectureDetailInfo
import com.suwiki.model.LectureMain
import com.suwiki.model.Result
import com.suwiki.remote.api.LectureApi
import com.suwiki.remote.response.evaluation.toModel
import com.suwiki.remote.response.lecture.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteLectureDataSourceImpl @Inject constructor(
    private val lectureApi: LectureApi,
) : RemoteLectureDataSource {

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
