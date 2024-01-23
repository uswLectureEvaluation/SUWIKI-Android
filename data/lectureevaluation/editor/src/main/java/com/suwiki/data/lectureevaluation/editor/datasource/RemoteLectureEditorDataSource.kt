package com.suwiki.data.lectureevaluation.editor.datasource

interface RemoteLectureEditorDataSource {

  suspend fun postLectureEvaluation(
    lectureId: Long,
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
