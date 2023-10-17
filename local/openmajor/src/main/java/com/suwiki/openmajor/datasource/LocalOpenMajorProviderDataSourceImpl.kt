package com.suwiki.openmajor.datasource

import com.suwiki.data.datasource.local.LocalOpenMajorProviderDataSource
import com.suwiki.database.OpenMajorDatabase
import com.suwiki.model.OpenMajor
import com.suwiki.openmajor.converter.toModel
import javax.inject.Inject

class LocalOpenMajorProviderDataSourceImpl @Inject constructor(
    private val db: OpenMajorDatabase,
) : LocalOpenMajorProviderDataSource {
    override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
        return db.openMajorDao().getAll().map { it.toModel() }
    }
}
