package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalOpenMajorProviderDataSource
import com.suwiki.local.db.OpenMajorDatabase
import com.suwiki.local.model.toModel
import com.suwiki.model.OpenMajor
import javax.inject.Inject

class LocalOpenMajorProviderDataSourceImpl @Inject constructor(
    private val db: OpenMajorDatabase,
) : LocalOpenMajorProviderDataSource {
    override suspend fun getLocalOpenMajorList(): List<OpenMajor> {
        return db.openMajorDao().getAll().map { it.toModel() }
    }
}
