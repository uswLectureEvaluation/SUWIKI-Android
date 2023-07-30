package com.suwiki.data.datasource.remote

import com.suwiki.model.Evaluation
import com.suwiki.model.LectureDetailEvaluationData
import com.suwiki.model.Result

interface RemoteEvaluateDataSource {

    suspend fun getEvaluatePosts(page: Int): Result<List<Evaluation>>

    suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData>

    suspend fun postLectureEvaluation(
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
    ): Result<Unit>

    suspend fun updateLectureEvaluation(
        lectureId: Long,
        selectedSemester: String,
        satisfaction: Float,
        learning: Float,
        honey: Float,
        team: Int,
        difficulty: Int,
        homework: Int,
        content: String,
    ): Result<Unit>

    suspend fun deleteEvaluation(id: Long): Result<Unit>

    suspend fun reportLecture(
        evaluateIdx: Long,
        content: String = "",
    ): Result<Unit>
}
