package com.kunize.uswtimetable.repository.evaluation

class EvaluationRepository(
    private val remoteDataSource: EvaluationRemoteDataSource
) {
    suspend fun getLectureMainList(option: String, majorType: String) =
        remoteDataSource.getEvaluationDataSource(option, majorType = majorType)
}

