package com.suwiki.remote.notice.datasource

import com.suwiki.core.model.notice.Notice
import com.suwiki.core.model.notice.NoticeDetail
import com.suwiki.data.datasource.remote.RemoteNoticeDataSource
import com.suwiki.remote.notice.api.NoticeApi
import com.suwiki.remote.notice.response.toModel
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : RemoteNoticeDataSource {

    override suspend fun getNoticeList(page: Int): List<Notice> {
        return noticeApi.getNoticeList(page).getOrThrow().data.map { it.toModel() }
    }

    override suspend fun getNotice(id: Long): NoticeDetail {
        return noticeApi.getNotice(id).getOrThrow().data.toModel()
    }
}