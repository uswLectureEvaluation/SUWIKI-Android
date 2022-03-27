package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit

class NoticeRemoteDataSource: NoticeDataSource {
    override suspend fun getNotices() = IRetrofit.getInstanceWithNoToken().getNoticeList()
}