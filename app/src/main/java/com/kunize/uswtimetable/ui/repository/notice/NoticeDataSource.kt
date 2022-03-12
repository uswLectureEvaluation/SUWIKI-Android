package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeDto

interface NoticeDataSource {

    suspend fun getNotices(): List<NoticeDto>
}