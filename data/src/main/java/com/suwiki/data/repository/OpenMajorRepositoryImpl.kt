package com.suwiki.data.repository

import com.suwiki.data.datasource.OpenMajorDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.OpenMajorVersion
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.OpenMajorRepository
import javax.inject.Inject

class OpenMajorRepositoryImpl @Inject constructor(
    private val remoteDataSource: OpenMajorDataSource,
) : OpenMajorRepository {
    override suspend fun getOpenMajorVersion(): Result<OpenMajorVersion> {
        return remoteDataSource.getOpenMajorVersion().mapOnSuccess { it.toDomain() }
    }

    override suspend fun getOpenMajorList(): Result<List<String>> {
        return remoteDataSource.getOpenMajorList().mapOnSuccess { it.data }
    }

    override suspend fun bookmarkMajor(majorName: String): Result<String> {
        return remoteDataSource.bookmarkMajor(majorName)
    }

    override suspend fun getBookmarkMajorList(): Result<List<String>> {
        return remoteDataSource.getBookmarkMajorList().mapOnSuccess { it.data }
    }

    override suspend fun clearBookmarkMajor(majorName: String): Result<String> {
        return remoteDataSource.clearBookmarkMajor(majorName)
    }
}
