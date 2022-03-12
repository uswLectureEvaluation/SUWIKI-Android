package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeDetailDto

interface NoticeDetailDataSource {
    suspend fun getNotice(id: Long): NoticeDetailDto
}