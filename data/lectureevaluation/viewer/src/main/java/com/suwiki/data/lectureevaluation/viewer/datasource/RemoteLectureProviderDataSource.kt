package com.suwiki.data.lectureevaluation.viewer.datasource

import com.suwiki.core.model.lectureevaluation.LectureDetailEvaluationData
import com.suwiki.core.model.lectureevaluation.LectureDetailInfo
import com.suwiki.core.model.lectureevaluation.LectureMain

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
