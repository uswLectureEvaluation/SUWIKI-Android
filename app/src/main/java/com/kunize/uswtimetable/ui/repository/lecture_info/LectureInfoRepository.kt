package com.kunize.uswtimetable.ui.repository.lecture_info

import com.kunize.uswtimetable.dataclass.LectureDetailEvaluationDto
import com.kunize.uswtimetable.dataclass.LectureDetailExamDto
import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import retrofit2.Response

class LectureInfoRepository(
    private val remoteDataSource: LectureInfoRemoteDataSource
) {
    suspend fun getLectureDetailInfo(lectureId: Long): Response<LectureDetailInfoDto> {
        return remoteDataSource.getLectureDetailInfoDataSource(lectureId)
    }

    suspend fun getLectureDetailEvaluation(lectureId: Long, page: Int): Response<LectureDetailEvaluationDto> {
        return remoteDataSource.getLectureDetailEvaluationDataSource(lectureId, page)
    }

    suspend fun getLectureDetailExam(lectureId: Long, page: Int): Response<LectureDetailExamDto> {
        return remoteDataSource.getLectureDetailExamDataSource(lectureId, page)
    }

    suspend fun buyExam(lectureId: Long): Response<String> {
        return remoteDataSource.buyExam(lectureId)
    }
}