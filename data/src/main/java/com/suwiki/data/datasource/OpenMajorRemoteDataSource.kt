package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.OpenMajorListDto
import com.suwiki.data.network.dto.OpenMajorVersionDto
import com.suwiki.data.network.toResult
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.model.Result
import javax.inject.Inject

class OpenMajorRemoteDataSource @Inject constructor(
    @OtherApiService private val apiService: ApiService,
) : OpenMajorDataSource {
    override suspend fun getOpenMajorVersion(): Result<OpenMajorVersionDto> {
        return apiService.getOpenMajorVersion().toResult()
    }

    override suspend fun getOpenMajorList(): Result<OpenMajorListDto> {
        return apiService.getOpenMajorList().toResult()
    }

    override suspend fun bookmarkMajor(majorName: String): Result<String> {
        return apiService.bookmarkMajor(majorName).toResult()
    }

    override suspend fun getBookmarkMajorList(): Result<OpenMajorListDto> {
        return apiService.getBookmarkMajorList().toResult()
    }

    override suspend fun clearBookmarkMajor(majorName: String): Result<String> {
        return apiService.clearBookmarkMajor(majorName).toResult()
    }
}
