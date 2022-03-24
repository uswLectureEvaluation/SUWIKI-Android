package com.kunize.uswtimetable.ui.repository.notice

class NoticeRepository(private val dataSource: NoticeRemoteDataSource) {
    suspend fun getNotices() = dataSource.getNotices()
}