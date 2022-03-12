package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeDetailRepository
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.launch

class NoticeDetailViewModel(private val repository: NoticeDetailRepository) : ViewModel() {
    private val _notice = MutableLiveData<NoticeDetailDto>()
    val notice: LiveData<NoticeDetailDto> get() = _notice
    private val _isLoading = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = _isLoading

    fun getNotice(id: Long) {
        Log.d(TAG, "NoticeDetailViewModel - getNotice() called")
        viewModelScope.launch {
            val item = repository.getNotice(id)
            Log.d(TAG, "NoticeDetailViewModel - notice: $item")
            _notice.value = item
            _isLoading.value = false
        }
    }
}