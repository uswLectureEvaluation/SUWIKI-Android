package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteEvaluateDataSource
import com.suwiki.model.Evaluation
import com.suwiki.model.LectureDetailEvaluationData
import com.suwiki.model.Result
import com.suwiki.remote.api.EvaluateApi
import com.suwiki.remote.request.evaluation.LectureEvaluationRequest
import com.suwiki.remote.request.evaluation.ReportLectureRequest
import com.suwiki.remote.request.evaluation.UpdateLectureEvaluationRequest
import com.suwiki.remote.response.evaluation.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteEvaluateDataSourceImpl @Inject constructor(
    private val evaluateApi: EvaluateApi,
) : RemoteEvaluateDataSource {
    override suspend fun getEvaluatePosts(page: Int): Result<List<Evaluation>> {
        return evaluateApi.getEvaluatePosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData> {
        return evaluateApi.getLectureDetailEvaluation(lectureId = lectureId, page = page).toResult()
            .map {
                it.toModel()
            }
    }

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

    override suspend fun reportLecture(evaluateIdx: Long, content: String): Result<Unit> {
        return evaluateApi.reportLecture(
            ReportLectureRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}
