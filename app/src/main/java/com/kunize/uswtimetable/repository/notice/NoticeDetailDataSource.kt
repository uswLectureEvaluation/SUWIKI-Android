package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.data.remote.DataDto
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import retrofit2.Response

interface NoticeDetailDataSource {
    suspend fun getNotice(id: Long): Response<DataDto<NoticeDetailDto>>
}