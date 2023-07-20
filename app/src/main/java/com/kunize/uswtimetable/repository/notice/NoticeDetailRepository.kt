package com.kunize.uswtimetable.repository.notice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoticeDetailRepository @Inject constructor(
    private val dataSource: NoticeDetailDataSource,
) {
    suspend fun getNotice(id: Long) = withContext(Dispatchers.IO) {
        dataSource.getNotice(id)
    }
}
