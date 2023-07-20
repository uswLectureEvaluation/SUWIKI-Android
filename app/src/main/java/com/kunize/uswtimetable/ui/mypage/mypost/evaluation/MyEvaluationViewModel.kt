package com.kunize.uswtimetable.ui.mypage.mypost.evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.mypage.mypost.MyPostResult
import com.kunize.uswtimetable.util.LAST_PAGE
import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.model.MyEvaluation
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.MyPostRepository
import com.suwiki.domain.usecase.GetUserInfoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyEvaluationViewModel @Inject constructor(
    private val repository: MyPostRepository,
    getUserInfoUsecase: GetUserInfoUsecase,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()
    private val _resultFlow = MutableSharedFlow<MyPostResult>()
    val resultFlow = _resultFlow.asSharedFlow()
    val loading = MutableLiveData(false)

    private val _items = MutableLiveData<List<MyEvaluation>>(emptyList())
    val items: LiveData<List<MyEvaluation>> get() = _items

    private var _page = 1
    private var _loadFinished = false

    val isLoggedIn: StateFlow<Boolean> = getUserInfoUsecase.isLoggedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false,
        )

    val userInfo: StateFlow<LoggedInUser> = getUserInfoUsecase().filterNotNull()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoggedInUser(),
        )

    init {
        scrollBottomEvent()
    }

    fun scrollBottomEvent() {
        if (_loadFinished || _page == LAST_PAGE) return

        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.getEvaluation(_page)
            when (response) {
                is Result.Success -> {
                    val newData = response.data
                    if (newData.isEmpty()) {
                        _loadFinished = true
                    } else {
                        (items.value?.toMutableList() ?: mutableListOf()).run {
                            addAll(newData)
                            _items.postValue(this)
                        }
                        nextPage()
                    }
                }

                is Result.Failure -> TODO()
            }
            loading.postValue(false)
        }
    }

    fun deletePost(id: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.deleteEvaluation(id)
                _items.value = _items.value?.filterNot { evaluation -> evaluation.id == id }
                _resultFlow.emit(MyPostResult.Success)
            }.onFailure {
                _resultFlow.emit(MyPostResult.Fail)
            }
        }
    }

    fun editButtonClickEvent(data: MyEvaluation) {
        event(Event.EditEvent(data))
    }

    fun deleteButtonClickEvent(data: MyEvaluation) {
        event(Event.DeleteEvent(data))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun nextPage() {
        if (_page != LAST_PAGE) _page++
    }
}
