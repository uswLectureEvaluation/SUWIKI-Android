package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.repository.notice.NoticeRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 10

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent: SharedFlow<Event> = _uiEvent.asSharedFlow()
    var loading = MutableLiveData(false)

    private val _items = MutableLiveData<List<NoticeDto>>(emptyList())
    val items: LiveData<List<NoticeDto>> get() = _items

    private var _page = 1
    private var _loadFinished = false

    init {
        scrollBottomEvent()
    }

    fun scrollBottomEvent() {
        if (_loadFinished || _page == LAST_PAGE) return

        viewModelScope.launch {
            loading.postValue(true)
            val response = noticeRepository.getNoticeList(_page)
            if (response.isSuccessful) {
                val newData = response.body()?.data

                if (newData?.isEmpty() == true) {
                    _loadFinished = true
                } else {
                    val currentData = _items.value?.toMutableList() ?: mutableListOf()
                    currentData.addAll(newData!!)
                    _items.postValue(currentData)
                    nextPage()
                }
            }
            loading.postValue(false)
        }
    }

    fun noticeClicked(notice: NoticeDto) {
        event(Event.NoticeClickEvent(notice))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun nextPage() {
        if (_page != LAST_PAGE) _page++
    }

    sealed class Event {
        data class NoticeClickEvent(val notice: NoticeDto) : Event()
    }
}
