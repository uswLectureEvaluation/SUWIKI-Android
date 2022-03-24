package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository
import kotlinx.coroutines.*

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val notices = MutableLiveData<List<NoticeDto>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        getNotices()
    }

    private fun getNotices() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = noticeRepository.getNotices()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    notices.postValue(response.body())
                    loading.value = false
                } else {
                    onError("${response.code()} Error: ${response.message()} ${response.errorBody()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}