package com.suwiki.data.datasource.remote

interface RemoteOpenMajorDataSource {
    suspend fun getOpenMajorVersion(): Float
    suspend fun getOpenMajorList(): List<String>
    suspend fun bookmarkMajor(majorName: String)
    suspend fun removeBookmarkMajor(majorName: String)
}
