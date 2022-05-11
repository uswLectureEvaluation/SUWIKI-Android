package com.kunize.uswtimetable.repository.lecture_info

import com.kunize.uswtimetable.data.remote.LectureDetailEvaluationDto
import com.kunize.uswtimetable.data.remote.LectureDetailExamDto
import com.kunize.uswtimetable.data.remote.LectureDetailInfoDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class LectureInfoRemoteDataSource(private val apiService: IRetrofit): LectureInfoDataSource {

    override suspend fun getLectureDetailInfoDataSource(lectureId: Long): Response<LectureDetailInfoDto> {
        return apiService.getLectureDetailInfo(lectureId)
    }

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
}