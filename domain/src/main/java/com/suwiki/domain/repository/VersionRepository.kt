package com.suwiki.domain.repository

import com.suwiki.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface VersionRepository {
    val appVersion: String
    val openMajorVersion: Float
    val remoteAppVersion: Flow<Result<Long>>

    suspend fun setAppVersion(version: String)
    suspend fun setOpenMajorVersion(version: Float)
}
