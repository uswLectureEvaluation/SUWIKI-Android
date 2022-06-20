package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.repository.notice.NoticeRepository
import com.kunize.uswtimetable.ui.common.Event
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 10

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val notices = MutableLiveData<List<NoticeDto>>()
    private val _eventNotice = MutableLiveData<Event<Long>>()
    val eventNotice: LiveData<Event<Long>> get() = _eventNotice
    val items: Flow<PagingData<NoticeDto>> = Pager(
        config = PagingConfig(
            pageSize = ITEMS_PER_PAGE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { noticeRepository.pagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun moveToNoticeDetail(noticeId: Long) {
        _eventNotice.value = Event(noticeId)
    }
}