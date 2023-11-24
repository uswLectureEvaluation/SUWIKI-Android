package com.suwiki.remote.lectureevaluation.editor.datasource

import com.suwiki.data.lectureevaluation.editor.datasource.RemoteExamEditorDataSource
import com.suwiki.remote.lectureevaluation.editor.api.ExamEditorApi
import com.suwiki.remote.lectureevaluation.editor.request.PostExamRequest
import com.suwiki.remote.lectureevaluation.editor.request.UpdateExamRequest
import javax.inject.Inject

class RemoteExamEditorDataSourceImpl @Inject constructor(
  private val examApi: ExamEditorApi,
) : RemoteExamEditorDataSource {

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
    val request = PostExamRequest(
      lectureName = lectureName,
      professor = professor,
      selectedSemester = selectedSemester,
      examInfo = examInfo,
      examType = examType,
      examDifficulty = examDifficulty,
      content = content,
    )

    return examApi.postLectureExam(
      lectureId = lectureId,
      request = request,
    ).getOrThrow()
  }

  override suspend fun updateExamEvaluation(
    lectureId: Long,
    selectedSemester: String?,
    examInfo: String,
    examType: String?,
    examDifficulty: String,
    content: String,
  ) {
    val request = UpdateExamRequest(
      selectedSemester = selectedSemester,
      examInfo = examInfo,
      examType = examType,
      examDifficulty = examDifficulty,
      content = content,
    )

    return examApi.updateLectureExam(
      lectureId = lectureId,
      request = request,
    ).getOrThrow()
  }

  override suspend fun deleteExamEvaluation(id: Long) {
    return examApi.deleteExamInfo(id).getOrThrow()
  }
}
