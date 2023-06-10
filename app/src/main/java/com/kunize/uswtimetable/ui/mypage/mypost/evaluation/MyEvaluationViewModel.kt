package com.kunize.uswtimetable.ui.mypage.mypost.evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.domain.usecase.GetUserInfoUsecase
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.ui.mypage.mypost.Result
import com.kunize.uswtimetable.util.LAST_PAGE
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
    private val _resultFlow = MutableSharedFlow<Result>()
    val resultFlow = _resultFlow.asSharedFlow()
    val loading = MutableLiveData(false)

    private val _items = MutableLiveData<List<MyEvaluationDto>>(emptyList())
    val items: LiveData<List<MyEvaluationDto>> get() = _items

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

    fun deletePost(id: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.deleteEvaluation(id)
                _items.value = _items.value?.filterNot { evaluation -> evaluation.id == id }
                _resultFlow.emit(Result.Success)
            }.onFailure {
                _resultFlow.emit(Result.Fail)
            }
        }
    }

    fun editButtonClickEvent(data: MyEvaluationDto) {
        event(Event.EditEvent(data))
    }

    fun deleteButtonClickEvent(data: MyEvaluationDto) {
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
