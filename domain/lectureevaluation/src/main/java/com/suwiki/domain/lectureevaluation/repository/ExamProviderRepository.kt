package com.suwiki.domain.lectureevaluation.repository

import com.suwiki.common.model.lectureevaluation.exam.ExamEvaluationList

interface ExamProviderRepository {
  suspend fun buyExam(lectureId: Long)

  suspend fun getExamEvaluationList(
    lectureId: Long,
    page: Int,
  ): ExamEvaluationList
}
