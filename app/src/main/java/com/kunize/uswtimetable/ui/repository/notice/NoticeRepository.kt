package com.kunize.uswtimetable.ui.repository.notice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoticeRepository(private val dataSource: NoticeRemoteDataSource) {
    suspend fun getNotices() = withContext(Dispatchers.IO) { dataSource.getNotices() }
}