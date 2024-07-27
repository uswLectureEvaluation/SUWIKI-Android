package com.suwiki.data.notice.datasource

import com.suwiki.common.model.notice.Notice
import com.suwiki.common.model.notice.NoticeDetail

interface RemoteNoticeDataSource {

  suspend fun getNoticeList(page: Int): List<Notice>

  suspend fun getNoticeDetail(id: Long): NoticeDetail

  suspend fun checkUpdateMandatory(
    versionCode: Long,
  ): Boolean
}
