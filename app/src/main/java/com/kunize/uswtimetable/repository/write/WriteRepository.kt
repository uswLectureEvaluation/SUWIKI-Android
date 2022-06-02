package com.kunize.uswtimetable.repository.write

import com.kunize.uswtimetable.data.remote.LectureEvaluationEditDto
import com.kunize.uswtimetable.data.remote.LectureEvaluationPostDto
import com.kunize.uswtimetable.data.remote.LectureExamDto
import retrofit2.Response

class WriteRepository(private val remoteDataSource: WriteRemoteDataSource) {
    suspend fun postLectureEvaluation(
        lectureId: Long,
        info: LectureEvaluationPostDto
    ): Response<String> {
        return remoteDataSource.postLectureEvaluation(lectureId, info)
    }

    suspend fun postLectureExam(lectureId: Long, info: LectureExamDto) =
        remoteDataSource.postLectureExam(lectureId, info)

    suspend fun updateLectureEvaluation(lectureId: Long, info: LectureEvaluationEditDto) =
        remoteDataSource.updateLectureEvaluation(lectureId, info)

    suspend fun updateLectureExam(lectureId: Long, info: LectureExamDto) =
        remoteDataSource.updateLectureExam(lectureId, info)

}