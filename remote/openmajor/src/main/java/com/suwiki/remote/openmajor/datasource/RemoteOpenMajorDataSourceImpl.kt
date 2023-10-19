package com.suwiki.remote.openmajor.datasource

import com.suwiki.data.openmajor.datasource.RemoteOpenMajorDataSource
import com.suwiki.remote.openmajor.api.MajorApi
import com.suwiki.remote.openmajor.request.BookmarkMajorRequest
import javax.inject.Inject

class RemoteOpenMajorDataSourceImpl @Inject constructor(
    private val majorApi: MajorApi,
) : RemoteOpenMajorDataSource {
    override suspend fun getOpenMajorVersion(): Float {
        return majorApi.getOpenMajorVersion().getOrThrow().version
    }

    override suspend fun getOpenMajorList(): List<String> {
        return majorApi.getOpenMajorList().getOrThrow().data
    }

    override suspend fun bookmarkMajor(majorName: String) {
        return majorApi.bookmarkMajor(BookmarkMajorRequest(majorType = majorName)).getOrThrow()
            .run { }
    }

    override suspend fun removeBookmarkMajor(majorName: String) {
        return majorApi.removeBookmarkMajor(majorName = majorName).getOrThrow().run { }
    }
}
