package com.kunize.uswtimetable.repository.open_major

import com.kunize.uswtimetable.data.remote.MajorType
import com.kunize.uswtimetable.data.remote.OpenMajorList
import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response

class OpenMajorRepository(
    private val remoteDataSource: OpenMajorRemoteDataSource
) {
    suspend fun getOpenMajorVersion() : ApiResponse<OpenMajorVersion> {
        return remoteDataSource.getOpenMajorVersion()
    }

    suspend fun getOpenMajorList() : ApiResponse<OpenMajorList> {
        return remoteDataSource.getOpenMajorList()
    }

    suspend fun bookmarkMajor(majorName: MajorType): Response<String> {
        return remoteDataSource.bookmarkMajor(majorName)
    }

    suspend fun getBookmarkMajorList(): Response<OpenMajorList> {
        return remoteDataSource.getBookmarkMajorList()
    }

    suspend fun clearBookmarkMajor(majorName: String): Response<String> {
        return remoteDataSource.clearBookmarkMajor(majorName)
    }
}