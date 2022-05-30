package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit

class NoticeRemoteDataSource: NoticeDataSource {
    override suspend fun getNotices(page: Int) = IRetrofit.getInstanceWithNoToken().getNoticeList(page)
}