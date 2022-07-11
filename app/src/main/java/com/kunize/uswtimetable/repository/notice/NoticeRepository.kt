package com.kunize.uswtimetable.repository.notice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoticeRepository(private val dataSource: NoticeRemoteDataSource) {
    suspend fun getNoticeList(page: Int) = withContext(Dispatchers.IO) { dataSource.getNoticeList(page) }
}
