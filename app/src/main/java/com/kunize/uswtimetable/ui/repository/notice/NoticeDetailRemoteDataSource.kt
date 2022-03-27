package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit

class NoticeDetailRemoteDataSource : NoticeDetailDataSource {
    override suspend fun getNotice(id: Long) = IRetrofit.getInstanceWithNoToken().getNotice(id)
}