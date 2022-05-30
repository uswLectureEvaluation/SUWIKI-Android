package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.repository.notice.NoticeRepository
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val notices = MutableLiveData<List<NoticeDto>>()
    private val _eventNotice = MutableLiveData<Event<Long>>()
    val eventNotice: LiveData<Event<Long>> get() = _eventNotice

    init {
        getNotices()
    }

    fun moveToNoticeDetail(noticeId: Long) {
        _eventNotice.value = Event(noticeId)
    }

    private fun getNotices() {
        loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = noticeRepository.getNotices(1) // TODO 페이지네이션 구현
            Log.d(TAG, "NoticeViewModel - getNotices() called -> ${response.body()}")

            if (response.isSuccessful) {
                notices.postValue(response.body()?.data)
                if (response.body()?.data.isNullOrEmpty()) onError("공지사항이 없습니다")
            } else {
                when (response.code()) {
                    403 -> onError("${response.code()} Error: 권한이 없습니다")
                    500 -> onError("${response.code()} Error: 서버 에러")
                    else -> onError("${response.code()} Error: ${response.message()}")
                }
            }
            loading.postValue(false)
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }
}