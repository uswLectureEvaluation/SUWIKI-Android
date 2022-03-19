package com.kunize.uswtimetable.ui.repository.notice

import androidx.lifecycle.LiveData
import com.kunize.uswtimetable.dataclass.NoticeDetailDto

interface NoticeDetailDataSource {
    suspend fun getNotice(id: Long): LiveData<NoticeDetailDto>
}