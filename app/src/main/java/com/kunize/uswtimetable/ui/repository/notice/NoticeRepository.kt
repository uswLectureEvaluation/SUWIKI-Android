package com.kunize.uswtimetable.ui.repository.notice

import androidx.lifecycle.LiveData
import com.kunize.uswtimetable.dataclass.NoticeDto

class NoticeRepository(private val noticeRemoteDataSource: NoticeRemoteDataSource) {

    fun getNotices(page: Int?): LiveData<List<NoticeDto>> {
        return noticeRemoteDataSource.getNotices(page)
    }
}