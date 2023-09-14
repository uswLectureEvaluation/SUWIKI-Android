package com.suwiki.data.datasource.local

import com.suwiki.model.OpenMajor

interface LocalOpenMajorProviderDataSource {
    suspend fun getLocalOpenMajorList(): List<OpenMajor>
}
