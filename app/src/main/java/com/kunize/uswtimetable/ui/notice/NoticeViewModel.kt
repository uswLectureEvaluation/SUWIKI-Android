package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    private var _noticeList = MutableLiveData<List<NoticeDto>>()
    val noticeList: LiveData<List<NoticeDto>> get() = _noticeList

    init {
        getNotices()
    }

    private fun getNotices() {
        viewModelScope.launch {
            val notices = noticeRepository.getNotices()
            _noticeList.value = notices
        }
    }
}