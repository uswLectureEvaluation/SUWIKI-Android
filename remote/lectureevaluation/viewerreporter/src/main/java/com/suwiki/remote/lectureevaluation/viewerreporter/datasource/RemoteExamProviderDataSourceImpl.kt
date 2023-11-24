package com.suwiki.remote.lectureevaluation.viewerreporter.datasource

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList
import com.suwiki.data.lectureevaluation.viewerreporter.datasource.RemoteExamProviderDataSource
import com.suwiki.remote.lectureevaluation.viewerreporter.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.viewerreporter.response.exam.toModel
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
