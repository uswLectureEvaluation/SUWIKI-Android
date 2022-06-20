package com.kunize.uswtimetable.repository.notice

import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.notice.NoticePagingSource

class NoticeRemoteDataSource(private val api: IRetrofit) {
    fun pagingSource() = NoticePagingSource(api)
}