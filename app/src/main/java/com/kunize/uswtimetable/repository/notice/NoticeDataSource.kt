package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeListDto
import retrofit2.Response

interface NoticeDataSource {

    suspend fun getNotices(page: Int): Response<NoticeListDto>
}