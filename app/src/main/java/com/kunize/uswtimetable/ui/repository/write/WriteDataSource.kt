package com.kunize.uswtimetable.ui.repository.write

import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import retrofit2.Response

interface WriteDataSource {
    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String>
}