package com.suwiki.domain.lectureevaluation.repository

interface ExamEditorRepository {

  suspend fun postExamEvaluation(
    lectureId: Long,
    lectureName: String?,
    professor: String?,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  )

  suspend fun updateExamEvaluation(
    lectureId: Long,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  )

  suspend fun deleteExamEvaluation(id: Long)
}
