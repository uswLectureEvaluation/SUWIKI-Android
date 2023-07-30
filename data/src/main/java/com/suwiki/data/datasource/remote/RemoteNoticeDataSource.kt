package com.suwiki.data.datasource.remote

import com.suwiki.model.Notice
import com.suwiki.model.NoticeDetail
import com.suwiki.model.Result

interface RemoteNoticeDataSource {

    suspend fun getNoticeList(page: Int): Result<List<Notice>>

    suspend fun getNotice(id: Long): Result<NoticeDetail>
}
