package com.suwiki.data.datasource.remote

import com.suwiki.core.model.LectureDetailEvaluationData
import com.suwiki.core.model.LectureDetailInfo
import com.suwiki.core.model.LectureMain

interface RemoteLectureProviderDataSource {
    suspend fun getLectureMainList(
        option: String,
        page: Int = 1,
        majorType: String = "",
    ): List<LectureMain?>

    suspend fun getSearchResultDetail(
        searchValue: String,
        option: String,
        page: Int,
        majorType: String,
    ): List<LectureMain?>

    suspend fun getLectureDetailInfo(lectureId: Long): LectureDetailInfo

    suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): LectureDetailEvaluationData
}
