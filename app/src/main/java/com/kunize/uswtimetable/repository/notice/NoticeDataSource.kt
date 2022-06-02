package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.data.remote.DataDto
import com.kunize.uswtimetable.dataclass.NoticeDto
import retrofit2.Response

interface NoticeDataSource {

    suspend fun getNotices(page: Int): Response<DataDto<List<NoticeDto>>>
}