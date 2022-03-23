package com.kunize.uswtimetable.ui.repository.notice

import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Call

class NoticeRemoteDataSource: NoticeDataSource {

    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }

    override suspend fun getNotices(): Call<List<NoticeDto>> {
        return retrofit.getNoticeList()
    }
}