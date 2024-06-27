package com.suwiki.data.lectureevaluation.repository

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationList
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureProviderDataSource
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureProviderRepository
import javax.inject.Inject

class LectureProviderRepositoryImpl @Inject constructor(
  private val remoteLectureProviderDataSource: RemoteLectureProviderDataSource,
) : LectureProviderRepository {

  override suspend fun getLectureEvaluationList(lectureId: Long, page: Int): LectureEvaluationList {
    return remoteLectureProviderDataSource.getLectureEvaluationList(
      lectureId = lectureId,
      page = page,
    )
  }

  override suspend fun getLectureEvaluationAverageList(
    option: String,
    page: Int,
    majorType: String,
  ): List<LectureEvaluationAverage?> {
    return remoteLectureProviderDataSource.getLectureEvaluationAverageList(
      option = option,
      page = page,
      majorType = majorType,
    )
  }

  override suspend fun retrieveLectureEvaluationAverageList(
    search: String,
    option: String,
    page: Int,
    majorType: String,
  ): List<LectureEvaluationAverage?> {
    return remoteLectureProviderDataSource.retrieveLectureEvaluationAverageList(
      search = search,
      option = option,
      page = page,
      majorType = majorType,
    )
  }

  override suspend fun getLectureEvaluationExtraAverage(lectureId: Long): LectureEvaluationExtraAverage {
    return remoteLectureProviderDataSource.getLectureEvaluationExtraAverage(
      lectureId = lectureId,
    )
  }
}
