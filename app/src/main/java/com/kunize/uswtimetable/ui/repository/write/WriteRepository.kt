package com.kunize.uswtimetable.ui.repository.write

import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import retrofit2.Response

class WriteRepository(private val remoteDataSource: WriteRemoteDataSource) {
    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String> {
        return remoteDataSource.postLectureEvaluation(lectureId, info)
    }
}