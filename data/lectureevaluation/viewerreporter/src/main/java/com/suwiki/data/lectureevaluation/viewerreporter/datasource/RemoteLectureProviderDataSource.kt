package com.suwiki.data.lectureevaluation.viewerreporter.datasource

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationList
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage

interface RemoteLectureProviderDataSource {
  suspend fun getLectureMainList(
    option: String,
    page: Int = 1,
    majorType: String = "",
  ): List<LectureEvaluationAverage?>

  suspend fun getSearchResultDetail(
    searchValue: String,
    option: String,
    page: Int,
    majorType: String,
  ): List<LectureEvaluationAverage?>

  suspend fun getLectureDetailInfo(lectureId: Long): LectureEvaluationExtraAverage

  suspend fun getLectureDetailEvaluation(
    lectureId: Long,
    page: Int,
  ): LectureEvaluationList
}
