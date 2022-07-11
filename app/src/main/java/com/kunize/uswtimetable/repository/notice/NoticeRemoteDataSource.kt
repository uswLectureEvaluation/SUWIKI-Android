package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit

class NoticeRemoteDataSource(private val api: IRetrofit) {
    suspend fun getNoticeList(page: Int) = api.getNoticeList(page)
}
