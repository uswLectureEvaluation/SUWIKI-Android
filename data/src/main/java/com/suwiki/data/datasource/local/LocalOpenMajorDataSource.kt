package com.suwiki.data.datasource.local

import com.suwiki.model.OpenMajor

interface LocalOpenMajorDataSource {
    suspend fun saveAllOpenMajors(majors: List<OpenMajor>)
    suspend fun deleteAllOpenMajors()
    suspend fun getLocalOpenMajorList(): List<OpenMajor>
}
