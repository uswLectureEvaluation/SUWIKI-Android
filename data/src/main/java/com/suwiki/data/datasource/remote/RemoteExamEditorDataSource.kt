package com.suwiki.data.datasource.remote

import com.suwiki.model.Result

interface RemoteExamEditorDataSource {

    suspend fun postLectureExam(
        lectureId: Long,
        lectureName: String?,
        professor: String?,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ): Result<Unit>

    suspend fun updateLectureExam(
        lectureId: Long,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ): Result<Unit>

    suspend fun deleteExamInfo(id: Long): Result<Unit>
}
