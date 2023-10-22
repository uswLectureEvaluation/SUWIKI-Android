package com.suwiki.data.openmajor.datasource

interface RemoteOpenMajorDataSource {
  suspend fun getOpenMajorVersion(): Float
  suspend fun getOpenMajorList(): List<String>
  suspend fun bookmarkMajor(majorName: String)
  suspend fun removeBookmarkMajor(majorName: String)
}
