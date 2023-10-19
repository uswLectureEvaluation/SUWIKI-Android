package com.suwiki.data.openmajor.datasource

import com.suwiki.core.model.openmajor.OpenMajor

interface LocalOpenMajorStorageDataSource {
    suspend fun saveAllOpenMajors(majors: List<OpenMajor>)
    suspend fun deleteAllOpenMajors()
}
