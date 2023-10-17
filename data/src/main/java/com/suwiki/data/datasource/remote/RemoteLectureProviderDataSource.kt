package com.suwiki.data.datasource.remote

import com.suwiki.core.model.LectureDetailEvaluationData
import com.suwiki.core.model.LectureDetailInfo
import com.suwiki.core.model.LectureMain
import com.suwiki.core.model.Result

interface RemoteLectureProviderDataSource {
    suspend fun getLectureMainList(
        option: String,
        page: Int = 1,
        majorType: String = "",
    ): com.suwiki.core.model.Result<List<com.suwiki.core.model.LectureMain?>>

    suspend fun getSearchResultDetail(
        searchValue: String,
        option: String,
        page: Int,
        majorType: String,
    ): com.suwiki.core.model.Result<List<com.suwiki.core.model.LectureMain?>>

    suspend fun getLectureDetailInfo(lectureId: Long): com.suwiki.core.model.Result<com.suwiki.core.model.LectureDetailInfo>

    suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): com.suwiki.core.model.Result<com.suwiki.core.model.LectureDetailEvaluationData>
}
