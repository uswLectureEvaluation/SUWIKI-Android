package com.suwiki.data.datasource

import com.suwiki.data.db.request.ReportExamRequest
import com.suwiki.data.db.request.ReportLectureRequest
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.LectureDetailEvaluationDataDto
import com.suwiki.data.network.dto.LectureDetailExamDataDto
import com.suwiki.data.network.dto.LectureDetailInfoDto
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result

class LectureInfoDataSource(
    private val apiService: ApiService,
) {
    suspend fun getLectureDetailInfoDataSource(lectureId: Long): Result<LectureDetailInfoDto> =
        apiService.getLectureDetailInfo(lectureId).toResult().map { it.data }

    suspend fun getLectureDetailEvaluationDataSource(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationDataDto> {
        return apiService.getLectureDetailEvaluation(lectureId, page).toResult()
    }

    suspend fun getLectureDetailExamDataSource(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailExamDataDto> {
        return apiService.getLectureDetailExam(lectureId, page).toResult()
    }

    suspend fun buyExam(lectureId: Long): Result<String> {
        return apiService.buyExam(lectureId).toResult()
    }

    suspend fun reportLecture(lectureId: Long): Result<Unit> {
        return apiService.reportLecture(
            ReportLectureRequest(lectureId),
        ).toResult()
    }

    suspend fun reportExam(lectureId: Long): Result<Unit> {
        return apiService.reportExam(
            ReportExamRequest(lectureId),
        ).toResult()
    }
}
