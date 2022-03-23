package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeDto
import retrofit2.Call

interface NoticeDataSource {

    suspend fun getNotices(): Call<List<NoticeDto>>
}