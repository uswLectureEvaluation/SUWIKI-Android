package com.suwiki.domain.repository

import com.suwiki.domain.model.OpenMajor
import com.suwiki.domain.model.OpenMajorVersion
import com.suwiki.domain.model.Result

interface OpenMajorRepository {
  suspend fun getOpenMajorVersion(): Result<OpenMajorVersion>

  suspend fun getOpenMajorList(): Result<List<String>>

  suspend fun bookmarkMajor(majorName: String): Result<String>

  suspend fun getBookmarkMajorList(): Result<List<String>>

  suspend fun clearBookmarkMajor(majorName: String): Result<String>

  suspend fun saveAllOpenMajors(majors: List<OpenMajor>)

  suspend fun deleteAllOpenMajors()
  suspend fun getLocalOpenMajorList(): List<OpenMajor>
}
