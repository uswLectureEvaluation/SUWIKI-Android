package com.kunize.uswtimetable.repository.open_major

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

    suspend fun bookmarkMajor(majorName: String): Response<String> {
        return remoteDataSource.bookmarkMajor(majorName)
    }

    suspend fun getBookmarkMajorList(): Response<OpenMajorList> {
        return remoteDataSource.getBookmarkMajorList()
    }

    suspend fun clearBookmarkMajor(majorName: String): Response<String> {
        return remoteDataSource.clearBookmarkMajor(majorName)
    }
}