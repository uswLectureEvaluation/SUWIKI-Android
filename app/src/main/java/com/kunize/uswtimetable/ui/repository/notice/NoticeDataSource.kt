package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeDto
import retrofit2.Response

interface NoticeDataSource {

    suspend fun getNotices(): Response<List<NoticeDto>>
}