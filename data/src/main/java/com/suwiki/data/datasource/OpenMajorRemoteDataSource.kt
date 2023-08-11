package com.suwiki.data.datasource

import com.suwiki.data.db.OpenMajorDatabase
import com.suwiki.data.db.entity.converter.toDomain
import com.suwiki.data.db.entity.converter.toEntity
import com.suwiki.data.db.request.BookmarkMajorRequest
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.OpenMajorListDto
import com.suwiki.data.network.dto.OpenMajorVersionDto
import com.suwiki.data.network.toDomainResult
import com.suwiki.domain.di.AuthApiService
import com.suwiki.domain.model.OpenMajor
import com.suwiki.domain.model.Result
import javax.inject.Inject

class OpenMajorRemoteDataSource @Inject constructor(
    @AuthApiService private val apiService: ApiService,
    private val db: OpenMajorDatabase,
) : OpenMajorDataSource {
    override suspend fun getOpenMajorVersion(): Result<OpenMajorVersionDto> {
        return apiService.getOpenMajorVersion().toDomainResult()
    }

    override suspend fun getOpenMajorList(): Result<OpenMajorListDto> {
        return apiService.getOpenMajorList().toDomainResult()
    }

    override suspend fun bookmarkMajor(majorName: String): Result<String> {
        return apiService.bookmarkMajor(BookmarkMajorRequest(majorName)).toDomainResult()
    }

    override suspend fun getBookmarkMajorList(): Result<OpenMajorListDto> {
        return apiService.getBookmarkMajorList().toDomainResult()
    }

    override suspend fun clearBookmarkMajor(majorName: String): Result<String> {
        return apiService.clearBookmarkMajor(majorName).toDomainResult()
    }

    override suspend fun saveAllOpenMajors(majors: List<OpenMajor>) {
        db.openMajorDao().insertAll(majors.map { it.toEntity() })
    }

    override suspend fun deleteAllOpenMajors() {
        db.openMajorDao().deleteAll()
    }

    override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
        return db.openMajorDao().getAll().map { it.toDomain() }
    }
}
