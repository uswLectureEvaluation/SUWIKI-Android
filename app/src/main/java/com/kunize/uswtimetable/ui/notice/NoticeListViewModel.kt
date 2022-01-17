package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState

class NoticeListViewModel : ViewModel() {
    private val retrofitManager = RetrofitManager.instance

    private var _noticeList = MutableLiveData<List<NoticeDto>>()
    val noticeList: LiveData<List<NoticeDto>> get() = _noticeList

    private var _selected = MutableLiveData<NoticeDetailDto>()
    val selectedNotice : LiveData<NoticeDetailDto> get() = _selected

    init {
        retrofitManager.getNoticeList { state, list ->
            when(state) {
                ResponseState.OK -> {
                    _noticeList.value = list ?: listOf()
                }
                ResponseState.FAIL -> {
                    Log.d(TAG, "NoticeListViewModel - 공지 데이터 받아오기 실패")
                    // TODO 리스트 불러오기 실패한 경우 처리 방법 고민
                }
            }
        }
    }

    fun getNotice(id: Int) {
        retrofitManager.getNotice(id, completion = {state, result ->
            when(state) {
                ResponseState.OK -> {
                    _selected.value = result
                }
                ResponseState.FAIL -> {

                }
            }
        })
    }

}