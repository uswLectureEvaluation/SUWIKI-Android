package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    private val retrofitManager = RetrofitManager.instance
    private var _noticeList = MutableLiveData<List<NoticeDto>>()
    val noticeList: LiveData<List<NoticeDto>> get() = _noticeList

    init {
        getNotices()
        /*retrofitManager.getNoticeList { state, result ->
            when (state) {
                ResponseState.OK -> {
                    // result에 받아온 리스트 있음
                    _noticeList.value = result ?: listOf()
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                    Log.d(TAG, "NoticeViewModel - 공지 데이터 받아오기 실패")
                    // TODO 리스트를 불러오지 못했을 때 어떻게 처리할지 고민 고민
                }
            }
        }*/
    }

    private fun getNotices() {
        viewModelScope.launch {
            val notices = noticeRepository.getNotices()
            _noticeList.value = notices
            Log.d(TAG, "NoticeViewModel - ${_noticeList.value}")
        }
    }

    fun getNotice(id: Int) {
        retrofitManager.getNotice(id, completion = { state, result ->
            when (state) {
                ResponseState.OK -> {
                    // result에 받아온 공지 있음
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                }
            }
        })
    }
}