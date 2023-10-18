package com.suwiki.data.datasource.local

import com.suwiki.core.model.openmajor.OpenMajor

interface LocalOpenMajorProviderDataSource {
    suspend fun getLocalOpenMajorList(): List<OpenMajor>
}
