package com.suwiki.lectureevaluation.editor.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteLectureEditorDataSource
import com.suwiki.lectureevaluation.editor.api.LectureEditorApi
import com.suwiki.lectureevaluation.editor.request.LectureEvaluationRequest
import com.suwiki.lectureevaluation.editor.request.UpdateLectureEvaluationRequest
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteLectureEditorDataSourceImpl @Inject constructor(
    private val lectureApi: LectureEditorApi,
) : RemoteLectureEditorDataSource {

    override suspend fun postLectureEvaluation(
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
    ): Result<Unit> {
        val request = LectureEvaluationRequest(
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
            request,
        ).toResult()
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
    ): Result<Unit> {
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
        ).toResult()
    }

    override suspend fun deleteLectureEvaluation(id: Long): Result<Unit> {
        return lectureApi.deleteEvaluation(id = id).toResult()
    }
}