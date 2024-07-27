package com.suwiki.domain.lectureevaluation.repository

import com.suwiki.common.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.common.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.common.model.lectureevaluation.lecture.LectureEvaluationList

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
