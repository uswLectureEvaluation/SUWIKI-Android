package com.kunize.uswtimetable.repository.write

import com.kunize.uswtimetable.data.remote.LectureEvaluationEditDto
import com.kunize.uswtimetable.data.remote.LectureEvaluationPostDto
import com.kunize.uswtimetable.data.remote.LectureExamDto
import retrofit2.Response

interface WriteDataSource {
    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String>
    suspend fun postLectureExam(lectureId: Long, info: LectureExamDto): Response<String>
    suspend fun updateLectureEvaluation(lectureId: Long, info: LectureEvaluationEditDto): Response<String>
    suspend fun updateLectureExam(lectureId: Long, info: LectureExamDto): Response<String>
}