package com.kunize.uswtimetable.repository.start

import com.kunize.uswtimetable.data.remote.OpenMajorList
import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import retrofit2.Response

class OpenMajorRepository(
    private val remoteDataSource: OpenMajorRemoteDataSource
) {
    suspend fun getOpenMajorVersion() : Response<OpenMajorVersion> {
        return remoteDataSource.getOpenMajorVersion()
    }

    suspend fun getOpenMajorList() : Response<OpenMajorList> {
        return remoteDataSource.getOpenMajorList()
    }
}