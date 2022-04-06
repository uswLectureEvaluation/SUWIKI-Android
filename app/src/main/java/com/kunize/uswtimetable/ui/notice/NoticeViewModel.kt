package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val notices = MutableLiveData<List<NoticeDto>>()

    init {
        getNotices()
    }

    private fun getNotices() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = noticeRepository.getNotices()
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