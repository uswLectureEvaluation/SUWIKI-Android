package com.suwiki.data.datasource

import com.suwiki.data.network.dto.OpenMajorListDto
import com.suwiki.data.network.dto.OpenMajorVersionDto
import com.suwiki.domain.model.Result

interface OpenMajorDataSource {
    suspend fun getOpenMajorVersion(): Result<OpenMajorVersionDto>
    suspend fun getOpenMajorList(): Result<OpenMajorListDto>
    suspend fun bookmarkMajor(majorName: String): Result<String>
    suspend fun getBookmarkMajorList(): Result<OpenMajorListDto>
    suspend fun clearBookmarkMajor(majorName: String): Result<String>
}
