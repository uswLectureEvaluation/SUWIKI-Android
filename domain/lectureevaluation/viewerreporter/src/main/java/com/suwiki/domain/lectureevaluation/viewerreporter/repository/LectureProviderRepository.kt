package com.suwiki.domain.lectureevaluation.viewerreporter.repository

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationList
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage

interface LectureProviderRepository {
  suspend fun getLectureEvaluationAverageList(
    option: String,
    page: Int = 1,
    majorType: String = "",
  ): List<LectureEvaluationAverage?>

  suspend fun retrieveLectureEvaluationAverageList(
    search: String,
    option: String,
    page: Int,
    majorType: String,
  ): List<LectureEvaluationAverage?>

  suspend fun getLectureEvaluationExtraAverage(lectureId: Long): LectureEvaluationExtraAverage

  suspend fun getLectureEvaluationList(
    lectureId: Long,
    page: Int,
  ): LectureEvaluationList
}
