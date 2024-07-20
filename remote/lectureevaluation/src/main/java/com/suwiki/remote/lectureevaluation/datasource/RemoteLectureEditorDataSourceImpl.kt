package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.data.lectureevaluation.datasource.RemoteLectureEditorDataSource
import com.suwiki.remote.lectureevaluation.api.LectureEditorApi
import com.suwiki.remote.lectureevaluation.request.PostLectureEvaluationRequest
import com.suwiki.remote.lectureevaluation.request.UpdateLectureEvaluationRequest
import javax.inject.Inject

class RemoteLectureEditorDataSourceImpl @Inject constructor(
  private val lectureApi: LectureEditorApi,
) : RemoteLectureEditorDataSource {

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
    val request = PostLectureEvaluationRequest(
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

    return lectureApi.postLectureEvaluation(
      lectureId,
      request,
    ).getOrThrow()
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
    val request = UpdateLectureEvaluationRequest(
      selectedSemester = selectedSemester,
      satisfaction = satisfaction,
      learning = learning,
      honey = honey,
      team = team,
      difficulty = difficulty,
      homework = homework,
      content = content,
    )

    return lectureApi.updateLectureEvaluation(
      lectureId = lectureId,
      updateLectureEvaluationRequest = request,
    ).getOrThrow()
  }

  override suspend fun deleteLectureEvaluation(id: Long) {
    return lectureApi.deleteLectureEvaluation(id = id).getOrThrow()
  }
}
