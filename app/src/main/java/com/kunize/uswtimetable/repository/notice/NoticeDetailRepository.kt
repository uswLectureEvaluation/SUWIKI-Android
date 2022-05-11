package com.kunize.uswtimetable.repository.notice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoticeDetailRepository(private val dataSource: NoticeDetailRemoteDataSource) {

    suspend fun getNotice(id: Long) = withContext(Dispatchers.IO) {
        dataSource.getNotice(id)
    }
}