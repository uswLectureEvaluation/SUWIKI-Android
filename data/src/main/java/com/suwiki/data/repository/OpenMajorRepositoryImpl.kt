package com.suwiki.data.repository

import com.suwiki.data.datasource.OpenMajorDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.di.IoDispatcher
import com.suwiki.domain.model.OpenMajor
import com.suwiki.domain.model.OpenMajorVersion
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.OpenMajorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OpenMajorRepositoryImpl @Inject constructor(
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
  private val dataSource: OpenMajorDataSource,
) : OpenMajorRepository {
  override suspend fun getOpenMajorVersion(): Result<OpenMajorVersion> {
    return dataSource.getOpenMajorVersion().map { it.toDomain() }
  }

  override suspend fun getOpenMajorList(): Result<List<String>> {
    return dataSource.getOpenMajorList().map { it.data }
  }

  override suspend fun bookmarkMajor(majorName: String): Result<String> {
    return dataSource.bookmarkMajor(majorName)
  }

  override suspend fun getBookmarkMajorList(): Result<List<String>> {
    return dataSource.getBookmarkMajorList().map { it.data }
  }

  override suspend fun clearBookmarkMajor(majorName: String): Result<String> {
    return dataSource.clearBookmarkMajor(majorName)
  }

  override suspend fun saveAllOpenMajors(majors: List<OpenMajor>) {
    withContext(ioDispatcher) { dataSource.saveAllOpenMajors(majors) }
  }

  override suspend fun deleteAllOpenMajors() {
    withContext(ioDispatcher) { dataSource.deleteAllOpenMajors() }
  }

  override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
    return withContext(ioDispatcher) { dataSource.getLocalOpenMajorList() }
  }
}
