package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.NoticeDto
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result
import javax.inject.Inject

class NoticeDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getNoticeList(page: Int): Result<List<NoticeDto>> =
        apiService.getNoticeList(page).toResult().map { it.data }
}
