package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit

class NoticeRemoteDataSource(private val apiService: IRetrofit): NoticeDataSource {
    override suspend fun getNotices() = apiService.getNoticeList()
}