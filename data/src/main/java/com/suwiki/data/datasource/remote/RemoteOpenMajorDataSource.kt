package com.suwiki.data.datasource.remote

import com.suwiki.model.Result

interface RemoteOpenMajorDataSource {
    suspend fun getOpenMajorVersion(): Result<Float>
    suspend fun getOpenMajorList(): Result<List<String>>
    suspend fun bookmarkMajor(majorName: String): Result<Unit>
    suspend fun removeBookmarkMajor(majorName: String): Result<Unit>
}
