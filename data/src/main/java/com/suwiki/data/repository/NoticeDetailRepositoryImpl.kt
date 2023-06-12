package com.suwiki.data.repository

import com.suwiki.data.datasource.NoticeDetailDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.Notice
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.NoticeDetailRepository
import javax.inject.Inject

class NoticeDetailRepositoryImpl @Inject constructor(
    private val dataSource: NoticeDetailDataSource,
) : NoticeDetailRepository {
    override suspend fun getNotice(id: Long): Result<Notice> =
        dataSource.getNotice(id).map { it.toDomain() }
}
