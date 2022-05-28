package com.kunize.uswtimetable.repository.evaluation

import com.kunize.uswtimetable.data.remote.LectureMainDto
import retrofit2.Response

class EvaluationRepository(
    private val remoteDataSource: EvaluationRemoteDataSource
) {
    suspend fun getLectureMainList(option: String, majorType: String): Response<LectureMainDto> {
        return remoteDataSource.getEvaluationDataSource(option, majorType = majorType)
    }
}

