package com.suwiki.openmajor.datasource

import com.suwiki.data.datasource.local.LocalOpenMajorStorageDataSource
import com.suwiki.database.OpenMajorDatabase
import com.suwiki.model.OpenMajor
import com.suwiki.openmajor.converter.toEntity
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