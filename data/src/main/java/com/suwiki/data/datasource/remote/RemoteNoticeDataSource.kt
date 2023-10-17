package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Notice
import com.suwiki.core.model.NoticeDetail
import com.suwiki.core.model.Result

interface RemoteNoticeDataSource {

    suspend fun getNoticeList(page: Int): com.suwiki.core.model.Result<List<com.suwiki.core.model.Notice>>

    suspend fun getNotice(id: Long): com.suwiki.core.model.Result<com.suwiki.core.model.NoticeDetail>
}
