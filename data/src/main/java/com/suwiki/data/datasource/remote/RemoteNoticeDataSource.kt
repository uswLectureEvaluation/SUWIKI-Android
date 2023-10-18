package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Notice
import com.suwiki.core.model.NoticeDetail

interface RemoteNoticeDataSource {

    suspend fun getNoticeList(page: Int): List<Notice>

    suspend fun getNotice(id: Long): NoticeDetail
}
