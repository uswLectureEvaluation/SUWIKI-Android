package com.suwiki.data.lectureevaluation.repository

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList
import com.suwiki.data.lectureevaluation.datasource.RemoteExamProviderDataSource
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamProviderRepository
import javax.inject.Inject

class ExamProviderRepositoryImpl @Inject constructor(
  private val remoteExamProviderDataSource: RemoteExamProviderDataSource,
) : ExamProviderRepository {
  override suspend fun buyExam(lectureId: Long) {
    remoteExamProviderDataSource.buyExam(lectureId = lectureId)
  }

  override suspend fun getExamEvaluationList(lectureId: Long, page: Int): ExamEvaluationList {
    return remoteExamProviderDataSource.getExamEvaluationList(
      lectureId = lectureId,
      page = page,
    )
  }
}
