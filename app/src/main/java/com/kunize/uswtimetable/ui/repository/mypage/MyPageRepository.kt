package com.kunize.uswtimetable.ui.repository.mypage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPageRepository(private val dataSource: MyPageRemoteDataSource) {
    suspend fun getUserData() = withContext(Dispatchers.IO) { dataSource.getUserData() }
}