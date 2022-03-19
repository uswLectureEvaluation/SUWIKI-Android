package com.kunize.uswtimetable.ui.repository.notice

import androidx.lifecycle.LiveData
import com.kunize.uswtimetable.dataclass.NoticeDto

interface NoticeDataSource {

    suspend fun getNotices(page: Int?): LiveData<List<NoticeDto>>
}