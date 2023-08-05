package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalOpenMajorDataSource
import com.suwiki.local.db.OpenMajorDatabase
import com.suwiki.local.model.toEntity
import com.suwiki.local.model.toModel
import com.suwiki.model.OpenMajor
import javax.inject.Inject

class LocalOpenMajorDataSourceImpl @Inject constructor(
    private val db: OpenMajorDatabase,
) : LocalOpenMajorDataSource {
    override suspend fun saveAllOpenMajors(majors: List<OpenMajor>) {
        db.openMajorDao().insertAll(majors.map { it.toEntity() })
    }
    override suspend fun deleteAllOpenMajors() {
        db.openMajorDao().deleteAll()
    }
    override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
        return db.openMajorDao().getAll().map { it.toModel() }
    }
}
