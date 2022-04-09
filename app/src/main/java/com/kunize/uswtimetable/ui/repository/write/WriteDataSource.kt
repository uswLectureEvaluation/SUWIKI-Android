package com.kunize.uswtimetable.ui.repository.write

import com.kunize.uswtimetable.dataclass.LectureEvaluationEditDto
import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import com.kunize.uswtimetable.dataclass.LectureExamEditDto
import com.kunize.uswtimetable.dataclass.LectureExamPostDto
import retrofit2.Response

interface WriteDataSource {
    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String>
    suspend fun postLectureExam(lectureId: Long, info: LectureExamPostDto): Response<String>
    suspend fun updateLectureEvaluation(lectureId: Long, info: LectureEvaluationEditDto): Response<String>
    suspend fun updateLectureExam(lectureId: Long, info: LectureExamEditDto): Response<String>
}