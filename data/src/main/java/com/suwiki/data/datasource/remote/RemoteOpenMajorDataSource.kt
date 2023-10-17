package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result

interface RemoteOpenMajorDataSource {
    suspend fun getOpenMajorVersion(): com.suwiki.core.model.Result<Float>
    suspend fun getOpenMajorList(): com.suwiki.core.model.Result<List<String>>
    suspend fun bookmarkMajor(majorName: String): com.suwiki.core.model.Result<Unit>
    suspend fun removeBookmarkMajor(majorName: String): com.suwiki.core.model.Result<Unit>
}
