package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeListDto
import retrofit2.Response

interface NoticeDataSource {

    suspend fun getNotices(): Response<NoticeListDto>
}