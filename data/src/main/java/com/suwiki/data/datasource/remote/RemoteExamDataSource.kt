package com.suwiki.data.datasource.remote

import com.suwiki.model.LectureDetailExamData
import com.suwiki.model.LectureExam
import com.suwiki.model.PurchaseHistory
import com.suwiki.model.Result

interface RemoteExamDataSource {

    suspend fun getExamPosts(page: Int): Result<List<LectureExam>>

    suspend fun getPurchaseHistory(): Result<List<PurchaseHistory>>

    suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailExamData>

    suspend fun buyExam(lectureId: Long): Result<Unit>

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

    suspend fun reportExam(
        evaluateIdx: Long,
        content: String = "",
    ): Result<Unit>
}
