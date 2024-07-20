package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.data.lectureevaluation.datasource.RemoteExamEditorDataSource
import com.suwiki.remote.lectureevaluation.api.ExamEditorApi
import com.suwiki.remote.lectureevaluation.request.PostExamEvaluationRequest
import com.suwiki.remote.lectureevaluation.request.UpdateExamEvaluationRequest
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
    val request = PostExamEvaluationRequest(
      lectureName = lectureName,
      professor = professor,
      selectedSemester = selectedSemester,
      examInfo = examInfo,
      examType = examType,
      examDifficulty = examDifficulty,
      content = content,
    )

    return examApi.postExamEvaluation(
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
    val request = UpdateExamEvaluationRequest(
      selectedSemester = selectedSemester,
      examInfo = examInfo,
      examType = examType,
      examDifficulty = examDifficulty,
      content = content,
    )

    return examApi.updateExamEvaluation(
      lectureId = lectureId,
      request = request,
    ).getOrThrow()
  }

  override suspend fun deleteExamEvaluation(id: Long) {
    return examApi.deleteExamEvaluation(id).getOrThrow()
  }
}
