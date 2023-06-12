package com.suwiki.data.repository

import com.suwiki.data.datasource.SearchResultDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.LectureMain
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.SearchResultRepository
import javax.inject.Inject

class SearchResultRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchResultDataSource,
) : SearchResultRepository {

    override suspend fun getSearchResultList(
        name: String,
        option: String,
        page: Int,
        majorType: String,
    ): Result<List<LectureMain?>> {
        return remoteDataSource.getSearchResultDataSource(name, option, page, majorType)
            .map { lectures ->
                lectures.map { it?.toDomain() }
            }
    }

    override suspend fun getLectureMainList(
        option: String,
        page: Int,
        majorType: String,
    ): Result<List<LectureMain?>> {
        return remoteDataSource.getEvaluationDataSource(option, page, majorType).map { lectures ->
            lectures.map { it?.toDomain() }
        }
    }
}
