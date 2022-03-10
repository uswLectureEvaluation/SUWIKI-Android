package com.kunize.uswtimetable.ui.repository.notice

import android.util.Log
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeRemoteDataSource(private val iRetrofit: IRetrofit): NoticeDataSource {

    override suspend fun getNotices(): List<NoticeDto> {
        Log.d(TAG, "NoticeRemoteDataSource - ${iRetrofit.getNoticeList()}")
        return iRetrofit.getNoticeList()
    }
}