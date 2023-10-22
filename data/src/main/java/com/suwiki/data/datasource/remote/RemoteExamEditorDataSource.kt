package com.suwiki.data.datasource.remote

interface RemoteExamEditorDataSource {

  suspend fun postLectureExam(
    lectureId: Long,
    lectureName: String?,
    professor: String?,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  )

  suspend fun updateLectureExam(
    lectureId: Long,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  )

  suspend fun deleteExamInfo(id: Long)
}
