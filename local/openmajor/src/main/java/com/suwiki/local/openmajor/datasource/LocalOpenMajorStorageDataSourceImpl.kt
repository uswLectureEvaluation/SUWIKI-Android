package com.suwiki.local.openmajor.datasource

import com.suwiki.core.database.OpenMajorDatabase
import com.suwiki.core.model.openmajor.OpenMajor
import com.suwiki.data.openmajor.datasource.LocalOpenMajorStorageDataSource
import com.suwiki.local.openmajor.converter.toEntity
import javax.inject.Inject

class LocalOpenMajorStorageDataSourceImpl @Inject constructor(
  private val db: OpenMajorDatabase,
) : LocalOpenMajorStorageDataSource {
  override suspend fun saveAllOpenMajors(majors: List<OpenMajor>) {
    db.openMajorDao().insertAll(majors.map { it.toEntity() })
  }

  override suspend fun deleteAllOpenMajors() {
    db.openMajorDao().deleteAll()
  }
}
