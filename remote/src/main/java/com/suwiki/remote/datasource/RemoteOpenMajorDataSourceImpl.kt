package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteOpenMajorDataSource
import com.suwiki.model.Result
import com.suwiki.remote.api.MajorApi
import com.suwiki.remote.request.BookmarkMajorRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

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
