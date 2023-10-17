package com.suwiki.data.datasource.local

import com.suwiki.core.model.OpenMajor

interface LocalOpenMajorStorageDataSource {
    suspend fun saveAllOpenMajors(majors: List<com.suwiki.core.model.OpenMajor>)
    suspend fun deleteAllOpenMajors()
}
