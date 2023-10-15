package com.suwiki.notice.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteNoticeDataSource
import com.suwiki.model.Notice
import com.suwiki.model.NoticeDetail
import com.suwiki.model.Result
import com.suwiki.notice.api.NoticeApi
import com.suwiki.notice.response.toModel
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

    override suspend fun getNoticeList(page: Int): Result<List<Notice>> {
        return noticeApi.getNoticeList(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getNotice(id: Long): Result<NoticeDetail> {
        return noticeApi.getNotice(id).toResult().map { it.data.toModel() }
    }
}