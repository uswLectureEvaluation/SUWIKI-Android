package com.suwiki.data.datasource.remote

import com.suwiki.core.model.notice.Notice
import com.suwiki.core.model.notice.NoticeDetail

interface RemoteNoticeDataSource {

    suspend fun getNoticeList(page: Int): List<Notice>

    suspend fun getNotice(id: Long): NoticeDetail
}
