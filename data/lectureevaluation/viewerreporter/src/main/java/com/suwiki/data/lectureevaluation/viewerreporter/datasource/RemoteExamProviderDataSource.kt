package com.suwiki.data.lectureevaluation.viewerreporter.datasource

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList

interface RemoteExamProviderDataSource {
  suspend fun buyExam(lectureId: Long)

  suspend fun getLectureDetailExam(
    lectureId: Long,
    page: Int,
  ): ExamEvaluationList
}
