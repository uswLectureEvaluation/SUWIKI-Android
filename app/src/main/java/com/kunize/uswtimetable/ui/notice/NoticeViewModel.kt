package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.UiEvent
import com.kunize.uswtimetable.util.LAST_PAGE
import com.suwiki.domain.model.SimpleNotice
import com.suwiki.domain.model.onFailure
import com.suwiki.domain.model.onSuccess
import com.suwiki.domain.repository.NoticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 10

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent: SharedFlow<Event> = _uiEvent.asSharedFlow()

    private val _state = MutableStateFlow(NoticeListState())
    val stateFlow = _state.asStateFlow()
    val state = stateFlow.value

    init {
        scrollBottomEvent()
    }

    fun scrollBottomEvent() {
        if (state.loadFinished || state.page == LAST_PAGE) return

        viewModelScope.launch {
            reduce(state.copy(loading = false))
            noticeRepository.getNoticeList(state.page)
                .onSuccess { newData ->
                    if (newData.isEmpty()) {
                        reduce(state.copy(loadFinished = true))
                    } else {
                        val updated = state.data.toMutableList().apply { addAll(newData) }
                        reduce(state.copy(loading = false, data = updated))
                        nextPage()
                    }
                }.onFailure { error ->
                    reduce(state.copy(loading = false, error = error))
                }
            reduce(state.copy(loading = false))
        }
    }

    fun noticeClicked(notice: SimpleNotice) {
        event(Event.NoticeClickEvent(notice))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun nextPage() {
        if (state.page != LAST_PAGE) reduce(state.copy(page = state.page + 1))
    }

    sealed class Event : UiEvent {
        data class NoticeClickEvent(val notice: SimpleNotice) : Event()
    }

    private fun reduce(newState: NoticeListState) {
        _state.value = newState
    }
}
