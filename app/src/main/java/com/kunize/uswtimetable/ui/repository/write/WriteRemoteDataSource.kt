package com.kunize.uswtimetable.ui.repository.write

import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class WriteRemoteDataSource(private val apiService: IRetrofit): WriteDataSource {
    override suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String> {
        return apiService.postLectureEvaluation(lectureId, info)
    }
}