package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeDetailRepository
import kotlinx.coroutines.launch

class NoticeDetailViewModel(private val repository: NoticeDetailRepository) : ViewModel() {
    var notice = MutableLiveData<NoticeDetailDto>()
    private val _isLoading = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = _isLoading

    fun getNotice(id: Long) {
        viewModelScope.launch {
            notice = repository.getNotice(id) as MutableLiveData<NoticeDetailDto>
            _isLoading.value = false
        }
    }
}