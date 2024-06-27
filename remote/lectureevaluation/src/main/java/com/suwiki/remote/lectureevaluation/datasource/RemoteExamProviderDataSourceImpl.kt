package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList
import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteExamProviderDataSource
import com.suwiki.remote.lectureevaluation.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.response.toModel
import javax.inject.Inject

class RemoteExamProviderDataSourceImpl @Inject constructor(
  private val examApi: ExamViewerApi,
) : RemoteExamProviderDataSource {

  override suspend fun getExamEvaluationList(
    lectureId: Long,
    page: Int,
  ): ExamEvaluationList {
    return examApi.getExamEvaluationList(lectureId = lectureId, page = page)
      .getOrThrow().toModel()
  }

  override suspend fun buyExam(lectureId: Long) {
    return examApi.buyExam(lectureId).getOrThrow()
  }
}
