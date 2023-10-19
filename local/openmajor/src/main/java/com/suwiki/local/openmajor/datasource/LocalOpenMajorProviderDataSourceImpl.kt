package com.suwiki.local.openmajor.datasource

import com.suwiki.core.database.OpenMajorDatabase
import com.suwiki.core.model.openmajor.OpenMajor
import com.suwiki.data.openmajor.datasource.LocalOpenMajorProviderDataSource
import com.suwiki.local.openmajor.converter.toModel
import javax.inject.Inject

class LocalOpenMajorProviderDataSourceImpl @Inject constructor(
    private val db: OpenMajorDatabase,
) : LocalOpenMajorProviderDataSource {
    override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
        return db.openMajorDao().getAll().map { it.toModel() }
    }
}
