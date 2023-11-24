package com.suwiki.data.lectureevaluation.editor.repository

import com.suwiki.data.lectureevaluation.editor.datasource.RemoteExamEditorDataSource
import com.suwiki.domain.lectureevaluation.editor.repository.ExamEditorRepository
import javax.inject.Inject

class ExamEditorRepositoryImpl @Inject constructor(
  private val remoteExamEditorDataSource: RemoteExamEditorDataSource,
) : ExamEditorRepository {
  override suspend fun postExamEvaluation(
    lectureId: Long,
    lectureName: String?,
    professor: String?,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  ) {
    remoteExamEditorDataSource.postExamEvaluation(
      lectureId = lectureId,
      lectureName = lectureName,
      professor = professor,
      selectedSemester = selectedSemester,
      examInfo = examInfo,
      examType = examType,
      examDifficulty = examDifficulty,
      content = content,
    )
  }

  override suspend fun updateExamEvaluation(
    lectureId: Long,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  ) {
    remoteExamEditorDataSource.updateExamEvaluation(
      lectureId = lectureId,
      selectedSemester = selectedSemester,
      examInfo = examInfo,
      examType = examType,
      examDifficulty = examDifficulty,
      content = content,
    )
  }

  override suspend fun deleteExamEvaluation(id: Long) {
    remoteExamEditorDataSource.deleteExamEvaluation(id = id)
  }
}
