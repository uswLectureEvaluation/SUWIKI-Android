package com.suwiki.data.lectureevaluation.datasource

import com.suwiki.common.model.lectureevaluation.exam.ExamEvaluationList

interface RemoteExamProviderDataSource {
  suspend fun buyExam(lectureId: Long)

  suspend fun getExamEvaluationList(
    lectureId: Long,
    page: Int,
  ): ExamEvaluationList
}
