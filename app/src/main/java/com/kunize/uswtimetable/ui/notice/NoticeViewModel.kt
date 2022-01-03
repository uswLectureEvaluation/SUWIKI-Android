package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.ResponseState

class NoticeViewModel: ViewModel() {

    init {
        RetrofitManager.instance.getNoticeList { responseState, responseBody ->
            when(responseState) {
                ResponseState.OK -> {

                }
                ResponseState.FAIL -> {

                }
            }
        }
    }
}