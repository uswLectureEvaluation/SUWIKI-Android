package com.kunize.uswtimetable.ui.repository.notice

import android.util.Log
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeDetailRepository(private val dataSource: NoticeDetailRemoteDataSource) {

    suspend fun getNotice(id: Long): NoticeDetailDto {
        Log.d(TAG, "NoticeDetailRepository - getNotice() called")
        return dataSource.getNotice(id)
    }
}