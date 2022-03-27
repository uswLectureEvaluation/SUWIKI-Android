package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val notices = MutableLiveData<List<NoticeDto>>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        getNotices()
    }

    private fun getNotices() {
        viewModelScope.launch(exceptionHandler) {
            val response = noticeRepository.getNotices()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d(TAG, "NoticeViewModel - getNotices() called -> ${response.body()}")
                    notices.postValue(response.body()?.data)
                    if (response.body()?.data.isNullOrEmpty()) onError("공지사항이 없습니다")
                    loading.value = false
                } else {
                    when (response.code()) {
                        403 -> onError("${response.code()} Error: 권한이 없습니다")
                        500 -> onError("${response.code()} Error: 서버 에러")
                        else -> onError("${response.code()} Error: ${response.message()}")
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}