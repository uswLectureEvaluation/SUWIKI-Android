package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeDto

class NoticeRepository(private val noticeRemoteDataSource: NoticeRemoteDataSource) {

    suspend fun getNotices(): List<NoticeDto> {
        return noticeRemoteDataSource.getNotices()
    }
}