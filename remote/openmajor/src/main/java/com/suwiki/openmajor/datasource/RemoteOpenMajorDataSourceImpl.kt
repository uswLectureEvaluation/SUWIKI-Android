package com.suwiki.openmajor.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteOpenMajorDataSource
import com.suwiki.openmajor.api.MajorApi
import com.suwiki.openmajor.request.BookmarkMajorRequest
import javax.inject.Inject
import com.suwiki.model.Result

class RemoteOpenMajorDataSourceImpl @Inject constructor(
    private val majorApi: MajorApi,
) : RemoteOpenMajorDataSource {
    override suspend fun getOpenMajorVersion(): Result<Float> {
        return majorApi.getOpenMajorVersion().toResult().map { it.version }
    }

    override suspend fun getOpenMajorList(): Result<List<String>> {
        return majorApi.getOpenMajorList().toResult().map { it.data }
    }

    override suspend fun bookmarkMajor(majorName: String): Result<Unit> {
        return majorApi.bookmarkMajor(BookmarkMajorRequest(majorType = majorName)).toResult()
            .map { }
    }

    override suspend fun removeBookmarkMajor(majorName: String): Result<Unit> {
        return majorApi.removeBookmarkMajor(majorName = majorName).toResult().map { }
    }
}