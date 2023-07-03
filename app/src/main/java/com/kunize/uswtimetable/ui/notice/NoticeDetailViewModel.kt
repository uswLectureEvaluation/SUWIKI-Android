package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.Event
import com.suwiki.domain.model.onSuccess
import com.suwiki.domain.repository.NoticeDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    private val repository: NoticeDetailRepository,
) : ViewModel() {
    private val _eventBack = MutableLiveData<Event<Boolean>>()
    val eventBack: LiveData<Event<Boolean>> get() = _eventBack

    private val _stateFlow = MutableStateFlow(NoticeDetailState())
    val stateFlow = _stateFlow.asStateFlow()
    val state: NoticeDetailState get() = stateFlow.value

    fun getNotice(id: Long) {
        viewModelScope.launch {
            reduce(state.copy(loading = true))
            repository.getNotice(id)
                .onSuccess {
                    reduce(state.copy(notice = it, loading = false))
                }
            reduce(state.copy(loading = false))
        }
    }

    fun backToList() {
        _eventBack.value = Event(true)
    }

    private fun reduce(newState: NoticeDetailState) {
        _stateFlow.value = newState
    }
}
