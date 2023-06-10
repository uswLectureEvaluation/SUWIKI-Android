package com.kunize.uswtimetable.repository.open_major

import com.kunize.uswtimetable.data.remote.MajorType
import com.kunize.uswtimetable.data.remote.OpenMajorList
import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import javax.inject.Inject

class OpenMajorRemoteDataSource @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) : OpenMajorDataSource {
    override suspend fun getOpenMajorVersion(): ApiResponse<OpenMajorVersion> {
        return apiService.getOpenMajorVersion()
    }

    override suspend fun getOpenMajorList(): ApiResponse<OpenMajorList> {
        return apiService.getOpenMajorList()
    }

    override suspend fun bookmarkMajor(majorName: MajorType): Response<String> {
        return apiService.bookmarkMajor(majorName)
    }

    override suspend fun getBookmarkMajorList(): Response<OpenMajorList> {
        return apiService.getBookmarkMajorList()
    }

    override suspend fun clearBookmarkMajor(majorName: String): Response<String> {
        return apiService.clearBookmarkMajor(majorName)
    }
}
