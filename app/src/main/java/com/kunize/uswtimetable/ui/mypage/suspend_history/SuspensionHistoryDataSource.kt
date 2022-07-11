package com.kunize.uswtimetable.ui.mypage.suspend_history

import com.kunize.uswtimetable.data.remote.BlacklistDto
import com.kunize.uswtimetable.data.remote.SuspensionHistory
import com.skydoves.sandwich.ApiResponse

interface SuspensionHistoryDataSource {
    suspend fun getBanHistory(): ApiResponse<List<SuspensionHistory>>
    suspend fun getBlacklistHistory(): ApiResponse<List<BlacklistDto>>
}
