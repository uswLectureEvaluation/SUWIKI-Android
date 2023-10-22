package com.suwiki.domain.repository

import com.suwiki.domain.model.Result
import com.suwiki.domain.model.SimpleNotice

interface NoticeRepository {
  suspend fun getNoticeList(page: Int): Result<List<SimpleNotice>>
}
