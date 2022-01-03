package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.RESPONSE_STATE

class NoticeViewModel: ViewModel() {

    init {
        RetrofitManager.instance.getNoticeList { responseState, responseBody ->
            when(responseState) {
                RESPONSE_STATE.OK -> {

                }
                RESPONSE_STATE.FAIL -> {

                }
            }
        }
    }
}