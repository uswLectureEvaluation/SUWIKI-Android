package com.suwiki.remote.notice.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteNoticeDataSource
import com.suwiki.core.model.Notice
import com.suwiki.core.model.NoticeDetail
import com.suwiki.core.model.Result
import com.suwiki.remote.notice.api.NoticeApi
import com.suwiki.remote.notice.response.toModel
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

    override suspend fun getNoticeList(page: Int): com.suwiki.core.model.Result<List<com.suwiki.core.model.Notice>> {
        return noticeApi.getNoticeList(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getNotice(id: Long): com.suwiki.core.model.Result<com.suwiki.core.model.NoticeDetail> {
        return noticeApi.getNotice(id).toResult().map { it.data.toModel() }
    }
}