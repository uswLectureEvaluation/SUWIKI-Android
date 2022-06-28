package com.kunize.uswtimetable.ui.mypage.suspend_history

import com.kunize.uswtimetable.retrofit.IRetrofit

class SuspensionRepository(private val apiService: IRetrofit) {
    suspend fun getSuspensionHistory() = apiService.getSuspensionHistory()
}