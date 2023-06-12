package com.suwiki.data.repository

import com.suwiki.data.datasource.EvaluationDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.LectureMain
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.EvaluationRepository

class EvaluationRepositoryImpl(
    private val dataSource: EvaluationDataSource,
) : EvaluationRepository {
    override suspend fun getLectureMainList(
        option: String,
        majorType: String,
    ): Result<List<LectureMain?>> {
        return dataSource.getEvaluationDataSource(option, majorType = majorType).map { lectures ->
            lectures.map { it?.toDomain() }
        }
    }
}
