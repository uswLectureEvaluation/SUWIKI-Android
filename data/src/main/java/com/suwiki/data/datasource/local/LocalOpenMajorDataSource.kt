package com.suwiki.data.datasource.local

import com.suwiki.domain.model.OpenMajor

interface LocalOpenMajorDataSource {
    suspend fun saveAllOpenMajors(majors: List<OpenMajor>)
    suspend fun deleteAllOpenMajors()
    suspend fun getLocalOpenMajorList(): List<OpenMajor>
}
