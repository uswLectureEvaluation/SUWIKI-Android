package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result

interface RemoteLectureEditorDataSource {

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
    ): com.suwiki.core.model.Result<Unit>

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
    ): com.suwiki.core.model.Result<Unit>

    suspend fun deleteLectureEvaluation(id: Long): com.suwiki.core.model.Result<Unit>
}
