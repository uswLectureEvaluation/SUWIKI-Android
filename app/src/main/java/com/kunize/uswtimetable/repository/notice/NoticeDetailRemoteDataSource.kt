package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class NoticeDetailRemoteDataSource @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) : NoticeDetailDataSource {
    override suspend fun getNotice(id: Long) = apiService.getNotice(id)
}
