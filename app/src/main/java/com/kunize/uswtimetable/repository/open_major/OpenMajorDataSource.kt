package com.kunize.uswtimetable.repository.open_major

import com.kunize.uswtimetable.data.remote.OpenMajorList
import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import retrofit2.Response

interface OpenMajorDataSource {
    suspend fun getOpenMajorVersion(): Response<OpenMajorVersion>
    suspend fun getOpenMajorList(): Response<OpenMajorList>
    suspend fun bookmarkMajor(majorName: String): Response<String>
    suspend fun getBookmarkMajorList(): Response<OpenMajorList>
    suspend fun clearBookmarkMajor(majorName: String): Response<String>
}