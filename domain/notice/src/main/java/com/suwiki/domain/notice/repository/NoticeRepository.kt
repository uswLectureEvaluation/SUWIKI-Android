package com.suwiki.domain.notice.repository

import com.suwiki.common.model.notice.Notice
import com.suwiki.common.model.notice.NoticeDetail

interface NoticeRepository {

  suspend fun getNoticeList(page: Int): List<Notice>

  suspend fun getNoticeDetail(id: Long): NoticeDetail

  suspend fun checkUpdateMandatory(
    versionCode: Long,
  ): Boolean
}
