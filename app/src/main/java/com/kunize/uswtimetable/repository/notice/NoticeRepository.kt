package com.kunize.uswtimetable.repository.notice

class NoticeRepository(private val dataSource: NoticeRemoteDataSource) {
    fun pagingSource() = dataSource.pagingSource()
}