package com.kunize.uswtimetable.repository.lecture_info

import com.kunize.uswtimetable.data.remote.DataDto
import com.kunize.uswtimetable.data.remote.LectureDetailEvaluationDto
import com.kunize.uswtimetable.data.remote.LectureDetailExamDto
import com.kunize.uswtimetable.data.remote.LectureDetailInfoDataDto
import retrofit2.Response

interface LectureInfoDataSource {
    suspend fun getLectureDetailInfoDataSource(lectureId: Long): Response<DataDto<LectureDetailInfoDataDto>>
    suspend fun getLectureDetailEvaluationDataSource(lectureId: Long, page: Int): Response<LectureDetailEvaluationDto>
    suspend fun getLectureDetailExamDataSource(lectureId: Long, page: Int): Response<LectureDetailExamDto>
    suspend fun buyExam(lectureId: Long): Response<String>
}