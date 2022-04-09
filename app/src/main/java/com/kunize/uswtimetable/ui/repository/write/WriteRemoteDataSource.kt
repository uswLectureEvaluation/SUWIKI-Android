package com.kunize.uswtimetable.ui.repository.write

import com.kunize.uswtimetable.dataclass.LectureEvaluationEditDto
import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import com.kunize.uswtimetable.dataclass.LectureExamEditDto
import com.kunize.uswtimetable.dataclass.LectureExamPostDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class WriteRemoteDataSource(private val apiService: IRetrofit): WriteDataSource {
    override suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String> {
        return apiService.postLectureEvaluation(lectureId, info)
    }

    override suspend fun postLectureExam(
        lectureId: Long,
        info: LectureExamPostDto
    ): Response<String> {
        return apiService.postLectureExam(lectureId, info)
    }

    override suspend fun updateLectureEvaluation(
        lectureId: Long,
        info: LectureEvaluationEditDto
    ): Response<String> {
        return apiService.updateLectureEvaluation(lectureId, info)
    }

    override suspend fun updateLectureExam(
        lectureId: Long,
        info: LectureExamEditDto
    ): Response<String> {
        return apiService.updateLectureExam(lectureId, info)
    }
}