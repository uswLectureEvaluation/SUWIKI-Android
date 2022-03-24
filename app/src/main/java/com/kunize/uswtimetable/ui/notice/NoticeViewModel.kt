package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.common.NetworkResult
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    private var _result = MutableLiveData<NetworkResult<List<NoticeDto>>>()
    val networkResult: LiveData<NetworkResult<List<NoticeDto>>> get() = _result

    init {
        getNotices()
        _result = noticeRepository.getResult()
    }

    private fun getNotices() {
        viewModelScope.launch {
            noticeRepository.getNotices()
        }
    }
}