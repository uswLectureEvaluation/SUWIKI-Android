package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class NoticeRemoteDataSource @Inject constructor(
    private val api: IRetrofit,
) {
    suspend fun getNoticeList(page: Int) = api.getNoticeList(page)
}
