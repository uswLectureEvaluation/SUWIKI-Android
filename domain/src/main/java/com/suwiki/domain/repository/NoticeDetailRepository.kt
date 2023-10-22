package com.suwiki.domain.repository

import com.suwiki.domain.model.Notice
import com.suwiki.domain.model.Result

interface NoticeDetailRepository {
  suspend fun getNotice(id: Long): Result<Notice>
}
