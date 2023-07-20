package com.suwiki.data.repository

import com.suwiki.data.datasource.NoticeDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.Result
import com.suwiki.domain.model.SimpleNotice
import com.suwiki.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val dataSource: NoticeDataSource,
) : NoticeRepository {
    override suspend fun getNoticeList(page: Int): Result<List<SimpleNotice>> =
        dataSource.getNoticeList(page).map { notices ->
            notices.map { it.toDomain() }
        }
}
