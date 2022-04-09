package com.kunize.uswtimetable.ui.repository.write

import com.kunize.uswtimetable.dataclass.LectureEvaluationEditDto
import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import com.kunize.uswtimetable.dataclass.LectureExamEditDto
import com.kunize.uswtimetable.dataclass.LectureExamPostDto
import retrofit2.Response

class WriteRepository(private val remoteDataSource: WriteRemoteDataSource) {
    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String> {
        return remoteDataSource.postLectureEvaluation(lectureId, info)
    }
    suspend fun postLectureExam(lectureId: Long, info: LectureExamPostDto): Response<String> {
        return remoteDataSource.postLectureExam(lectureId, info)
    }
    suspend fun updateLectureEvaluation(lectureId: Long, info: LectureEvaluationEditDto): Response<String> {
        return remoteDataSource.updateLectureEvaluation(lectureId, info)
    }
    suspend fun updateLectureExam(lectureId: Long, info: LectureExamEditDto): Response<String> {
        return remoteDataSource.updateLectureExam(lectureId, info)
    }
}