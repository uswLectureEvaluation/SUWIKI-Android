package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.NoticeData
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.ResponseState
import com.kunize.uswtimetable.util.isJsonArray
import com.kunize.uswtimetable.util.isJsonObject

class NoticeViewModel: ViewModel() {
    private val retrofitManager = RetrofitManager.instance
    private val _noticeList = MutableLiveData<List<NoticeData>>()
    val noticeList:LiveData<List<NoticeData>> get() = _noticeList

    init {
        retrofitManager.getNoticeList { state, result ->
            when(state) {
                ResponseState.OK -> {
                    // result에 받아온 리스트 있음
                    _noticeList.value = parseNotice(result)
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                    // TODO 리스트를 불러오지 못했을 때 어떻게 처리할지 고민 고민
                }
            }
        }
    }

    fun getNotice(id: Int) {
        retrofitManager.getNotice(id, completion = { state, result ->
            when(state) {
                ResponseState.OK -> {
                    // result에 받아온 공지 있음
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                }
            }
        })
    }

    private fun parseNotice(data: String): List<NoticeData> {
        // TODO 받아온 데이터를 리스트로 파싱하는 과정
        val notices = listOf<NoticeData>()
        when {
            data.isJsonArray() -> {

            }
            data.isJsonObject() -> {

            }
        }
        return notices
    }
}