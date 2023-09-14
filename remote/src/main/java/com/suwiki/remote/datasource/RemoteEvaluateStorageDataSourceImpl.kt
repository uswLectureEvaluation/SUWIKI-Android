package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteEvaluateStorageDataSource
import com.suwiki.model.Result
import com.suwiki.remote.api.EvaluateApi
import com.suwiki.remote.request.evaluation.LectureEvaluationRequest
import com.suwiki.remote.request.evaluation.UpdateLectureEvaluationRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteEvaluateStorageDataSourceImpl @Inject constructor(
    private val evaluateApi: EvaluateApi,
) : RemoteEvaluateStorageDataSource {

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

        return evaluateApi.postLectureEvaluation(
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

        return evaluateApi.updateLectureEvaluation(
            lectureId = lectureId,
            updateLectureEvaluationRequest = request,
        ).toResult()
    }

    override suspend fun deleteEvaluation(id: Long): Result<Unit> {
        return evaluateApi.deleteEvaluation(id = id).toResult()
    }
}
