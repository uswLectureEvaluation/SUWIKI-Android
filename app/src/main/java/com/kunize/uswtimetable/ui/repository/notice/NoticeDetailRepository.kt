package com.kunize.uswtimetable.ui.repository.notice

import androidx.lifecycle.LiveData
import com.kunize.uswtimetable.dataclass.NoticeDetailDto

class NoticeDetailRepository(private val dataSource: NoticeDetailRemoteDataSource) {

    suspend fun getNotice(id: Long): LiveData<NoticeDetailDto> {
        return dataSource.getNotice(id)
    }
}