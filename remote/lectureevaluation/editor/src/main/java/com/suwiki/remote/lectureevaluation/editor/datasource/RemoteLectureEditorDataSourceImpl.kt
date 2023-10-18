package com.suwiki.remote.lectureevaluation.editor.datasource

import com.suwiki.data.datasource.remote.RemoteLectureEditorDataSource
import com.suwiki.remote.lectureevaluation.editor.api.LectureEditorApi
import com.suwiki.remote.lectureevaluation.editor.request.LectureEvaluationRequest
import com.suwiki.remote.lectureevaluation.editor.request.UpdateLectureEvaluationRequest
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
    ) {
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
        return lectureApi.deleteEvaluation(id = id).getOrThrow()
    }
}