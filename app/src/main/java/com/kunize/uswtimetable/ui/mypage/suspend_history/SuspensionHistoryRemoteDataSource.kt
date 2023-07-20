package com.kunize.uswtimetable.ui.mypage.suspend_history

import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class SuspensionHistoryRemoteDataSource @Inject constructor(
    private val apiService: IRetrofit,
) : SuspensionHistoryDataSource {
    override suspend fun getBanHistory() = apiService.getSuspensionHistory()
    override suspend fun getBlacklistHistory() = apiService.getBlacklistHistory()
}
