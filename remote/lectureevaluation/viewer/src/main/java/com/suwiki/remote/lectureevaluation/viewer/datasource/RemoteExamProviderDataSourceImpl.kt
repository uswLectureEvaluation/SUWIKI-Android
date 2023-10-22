package com.suwiki.remote.lectureevaluation.viewer.datasource

import com.suwiki.core.model.lectureevaluation.LectureDetailExamData
import com.suwiki.data.lectureevaluation.viewer.datasource.RemoteExamProviderDataSource
import com.suwiki.remote.lectureevaluation.viewer.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.viewer.response.exam.toModel
import javax.inject.Inject

class RemoteExamProviderDataSourceImpl @Inject constructor(
  private val examApi: ExamViewerApi,
) : RemoteExamProviderDataSource {

  override suspend fun getLectureDetailExam(
    lectureId: Long,
    page: Int,
  ): LectureDetailExamData {
    return examApi.getLectureDetailExam(lectureId = lectureId, page = page)
      .getOrThrow().toModel()
  }

  override suspend fun buyExam(lectureId: Long) {
    return examApi.buyExam(lectureId).getOrThrow()
  }
}
