package com.suwiki.data.datasource.remote

import com.suwiki.model.LectureDetailInfo
import com.suwiki.model.LectureMain
import com.suwiki.model.Result

interface RemoteLectureDataSource {

    suspend fun getLectureMainList(
        option: String,
        page: Int = 1,
        majorType: String = "",
    ): Result<List<LectureMain?>>

    suspend fun getSearchResultDetail(
        searchValue: String,
        option: String,
        page: Int,
        majorType: String,
    ): Result<List<LectureMain?>>

    suspend fun getLectureDetailInfo(lectureId: Long): Result<LectureDetailInfo>
}
