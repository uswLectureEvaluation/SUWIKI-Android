package com.kunize.uswtimetable.repository.notice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoticeRepository @Inject constructor(
    private val dataSource: NoticeRemoteDataSource,
) {
    suspend fun getNoticeList(page: Int) =
        withContext(Dispatchers.IO) { dataSource.getNoticeList(page) }
}
