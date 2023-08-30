package com.suwiki.data.datasource.local

import com.suwiki.model.OpenMajor

interface LocalOpenMajorStorageDataSource {
    suspend fun saveAllOpenMajors(majors: List<OpenMajor>)
    suspend fun deleteAllOpenMajors()
}
