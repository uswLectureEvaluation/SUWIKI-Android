package com.kunize.uswtimetable.repository.lecture_info

import com.google.gson.JsonElement
import com.kunize.uswtimetable.data.remote.LectureDetailEvaluationDto
import com.kunize.uswtimetable.data.remote.LectureDetailExamDto
import com.kunize.uswtimetable.data.remote.ReportExam
import com.kunize.uswtimetable.data.remote.ReportLecture
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response

class LectureInfoRemoteDataSource(private val apiService: IRetrofit): LectureInfoDataSource {

    override suspend fun getLectureDetailInfoDataSource(lectureId: Long) = apiService.getLectureDetailInfo(lectureId)

    override suspend fun getLectureDetailEvaluationDataSource(lectureId: Long, page: Int): Response<LectureDetailEvaluationDto> {
        return apiService.getLectureDetailEvaluation(lectureId, page)
    }

    override suspend fun getLectureDetailExamDataSource(
        lectureId: Long,
        page: Int
    ): Response<LectureDetailExamDto> {
        return apiService.getLectureDetailExam(lectureId, page)
    }

    override suspend fun buyExam(lectureId: Long): Response<String> {
        return apiService.buyExam(lectureId)
    }

    override suspend fun reportLecture(lectureId: Long): ApiResponse<JsonElement> {
        return apiService.reportLecture(ReportLecture(lectureId))
    }

    override suspend fun reportExam(lectureId: Long): ApiResponse<JsonElement> {
        return apiService.reportExam(ReportExam(lectureId))
    }
}