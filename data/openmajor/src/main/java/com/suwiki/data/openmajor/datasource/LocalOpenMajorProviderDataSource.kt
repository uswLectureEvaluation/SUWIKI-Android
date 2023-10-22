package com.suwiki.data.openmajor.datasource

import com.suwiki.core.model.openmajor.OpenMajor

interface LocalOpenMajorProviderDataSource {
  suspend fun getLocalOpenMajorList(): List<OpenMajor>
}
