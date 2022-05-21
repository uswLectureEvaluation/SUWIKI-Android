package com.kunize.uswtimetable.repository.start

import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class OpenMajorRemoteDataSource(private val apiService: IRetrofit): OpenMajorDataSource {
    override suspend fun getOpenMajorVersion(): Response<OpenMajorVersion> {
        return apiService.getOpenMajorVersion()
    }
}