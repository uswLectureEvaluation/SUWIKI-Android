package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteExamDataSource
import com.suwiki.model.LectureDetailExamData
import com.suwiki.model.LectureExam
import com.suwiki.model.PurchaseHistory
import com.suwiki.model.Result
import com.suwiki.remote.api.ExamApi
import com.suwiki.remote.request.exam.PostLectureExamRequest
import com.suwiki.remote.request.exam.ReportExamRequest
import com.suwiki.remote.request.exam.UpdateLectureExamRequest
import com.suwiki.remote.response.exam.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteExamDataSourceImpl @Inject constructor(
    private val examApi: ExamApi,
) : RemoteExamDataSource {

    override suspend fun getExamPosts(page: Int): Result<List<LectureExam>> {
        return examApi.getExamPosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getPurchaseHistory(): Result<List<PurchaseHistory>> {
        return examApi.getPurchaseHistory().toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailExamData> {
        return examApi.getLectureDetailExam(lectureId = lectureId, page = page)
            .toResult().map { it.toModel() }
    }

    override suspend fun buyExam(lectureId: Long): Result<Unit> {
        return examApi.buyExam(lectureId).toResult()
    }

    override suspend fun postLectureExam(
        lectureId: Long,
        lectureName: String?,
        professor: String?,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ): Result<Unit> {
        val request = PostLectureExamRequest(
            lectureName = lectureName,
            professor = professor,
            selectedSemester = selectedSemester,
            examInfo = examInfo,
            examType = examType,
            examDifficulty = examDifficulty,
            content = content,
        )

        return examApi.postLectureExam(
            lectureId = lectureId,
            request = request,
        ).toResult()
    }

    override suspend fun updateLectureExam(
        lectureId: Long,
        selectedSemester: String?,
        examInfo: String,
        examType: String?,
        examDifficulty: String,
        content: String,
    ): Result<Unit> {
        val request = UpdateLectureExamRequest(
            selectedSemester = selectedSemester,
            examInfo = examInfo,
            examType = examType,
            examDifficulty = examDifficulty,
            content = content,
        )

        return examApi.updateLectureExam(
            lectureId = lectureId,
            request = request,
        ).toResult()
    }

    override suspend fun deleteExamInfo(id: Long): Result<Unit> {
        return examApi.deleteExamInfo(id).toResult()
    }

    override suspend fun reportExam(
        evaluateIdx: Long,
        content: String,
    ): Result<Unit> {
        return examApi.reportExam(
            ReportExamRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}
