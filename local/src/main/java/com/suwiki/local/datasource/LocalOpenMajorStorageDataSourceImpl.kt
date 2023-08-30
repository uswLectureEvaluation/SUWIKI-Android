package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalOpenMajorStorageDataSource
import com.suwiki.local.db.OpenMajorDatabase
import com.suwiki.local.model.toEntity
import com.suwiki.model.OpenMajor
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
