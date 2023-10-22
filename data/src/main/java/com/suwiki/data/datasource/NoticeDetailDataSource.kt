package com.suwiki.data.datasource

import com.suwiki.data.network.dto.NoticeDetailDto
import com.suwiki.domain.model.Result

interface NoticeDetailDataSource {
  suspend fun getNotice(id: Long): Result<NoticeDetailDto>
}
