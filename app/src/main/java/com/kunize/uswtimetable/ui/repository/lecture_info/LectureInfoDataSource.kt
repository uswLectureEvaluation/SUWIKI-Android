package com.kunize.uswtimetable.ui.repository.lecture_info

import com.kunize.uswtimetable.dataclass.LectureDetailEvaluationDto
import com.kunize.uswtimetable.dataclass.LectureDetailExamDto
import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import retrofit2.Response

interface LectureInfoDataSource {
    suspend fun getLectureDetailInfoDataSource(lectureId: Long): Response<LectureDetailInfoDto>
    suspend fun getLectureDetailEvaluationDataSource(lectureId: Long, page: Int): Response<LectureDetailEvaluationDto>
    suspend fun getLectureDetailExamDataSource(lectureId: Long, page: Int): Response<LectureDetailExamDto>
    suspend fun buyExam(lectureId: Long): Response<String>
}