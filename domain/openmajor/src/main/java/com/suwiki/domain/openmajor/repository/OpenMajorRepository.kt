package com.suwiki.domain.openmajor.repository

import kotlinx.coroutines.flow.Flow

interface OpenMajorRepository {
  suspend fun getOpenMajorList(): Flow<List<String>>
  suspend fun getBookmarkedOpenMajorList(): List<String>
  suspend fun bookmarkMajor(majorName: String)
  suspend fun removeBookmarkMajor(majorName: String)
}
