package com.kunize.uswtimetable.repository.start

import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import retrofit2.Response

interface OpenMajorDataSource {
    suspend fun getOpenMajorVersion(): Response<OpenMajorVersion>
}