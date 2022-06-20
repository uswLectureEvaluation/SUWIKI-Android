package com.kunize.uswtimetable.ui.notice

import android.util.Log
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
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private const val ITEMS_PER_PAGE = 10

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    val items: Flow<PagingData<NoticeDto>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { noticeRepository.pagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun noticeClicked(notice: NoticeDto) {
        Log.d(TAG, "NoticeViewModel - noticeClicked($notice) called")
        event(Event.NoticeClickEvent(notice))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class NoticeClickEvent(val notice: NoticeDto): Event()
    }
}