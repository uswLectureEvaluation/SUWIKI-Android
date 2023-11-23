package com.suwiki.domain.lectureevaluation.editor.repository

interface LectureEditorRepository {

  suspend fun postLectureEvaluation(
    lectureName: String,
    professor: String,
    selectedSemester: String,
    satisfaction: Float,
    learning: Float,
    honey: Float,
    team: Int,
    difficulty: Int,
    homework: Int,
    content: String,
  )

  suspend fun updateLectureEvaluation(
    lectureId: Long,
    selectedSemester: String,
    satisfaction: Float,
    learning: Float,
    honey: Float,
    team: Int,
    difficulty: Int,
    homework: Int,
    content: String,
  )

  suspend fun deleteLectureEvaluation(id: Long)
}
