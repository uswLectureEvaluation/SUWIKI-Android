package com.suwiki.data.lectureevaluation.editor.repository

import com.suwiki.data.lectureevaluation.editor.datasource.RemoteLectureEditorDataSource
import com.suwiki.domain.lectureevaluation.editor.repository.LectureEditorRepository
import javax.inject.Inject

class LectureEditorRepositoryImpl @Inject constructor(
  private val lectureEditorDataSource: RemoteLectureEditorDataSource,
) : LectureEditorRepository {
  override suspend fun postLectureEvaluation(
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
  ) {
    lectureEditorDataSource.postLectureEvaluation(
      lectureId = lectureId,
      lectureName = lectureName,
      professor = professor,
      selectedSemester = selectedSemester,
      satisfaction = satisfaction,
      learning = learning,
      honey = honey,
      team = team,
      difficulty = difficulty,
      homework = homework,
      content = content,
    )
  }

  override suspend fun updateLectureEvaluation(
    lectureId: Long,
    selectedSemester: String,
    satisfaction: Float,
    learning: Float,
    honey: Float,
    team: Int,
    difficulty: Int,
    homework: Int,
    content: String,
  ) {
    lectureEditorDataSource.updateLectureEvaluation(
      lectureId = lectureId,
      selectedSemester = selectedSemester,
      satisfaction = satisfaction,
      learning = learning,
      honey = honey,
      team = team,
      difficulty = difficulty,
      homework = homework,
      content = content,
    )
  }

  override suspend fun deleteLectureEvaluation(id: Long) {
    lectureEditorDataSource.deleteLectureEvaluation(id = id)
  }
}
