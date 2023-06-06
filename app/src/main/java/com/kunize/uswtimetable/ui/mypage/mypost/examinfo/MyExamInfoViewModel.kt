package com.kunize.uswtimetable.ui.mypage.mypost.examinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.data.remote.LectureExamDto
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.domain.usecase.GetUserInfoUsecase
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.ui.mypage.mypost.Result
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.LAST_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyExamInfoViewModel @Inject constructor(
    private val repository: MyPostRepository,
    getUserInfoUsecase: GetUserInfoUsecase,
) : ViewModel() {
    private val _items = MutableLiveData<List<LectureExamDto>>(emptyList())
    val items: LiveData<List<LectureExamDto>> get() = _items
    val loading = MutableLiveData<Boolean>()
    private val _resultFlow = MutableSharedFlow<Result>()
    val resultFlow = _resultFlow.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var _page = 1
    private var _loadFinished = false

    val isLoggedIn: StateFlow<Boolean> = getUserInfoUsecase.isLoggedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false,
        )

    val userInfo: StateFlow<LoggedInUser> = getUserInfoUsecase().transform {
        it?.let { info ->
            emit(info)
        }
    }.stateIn(
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
            val response = repository.getExamInfos(_page)
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

    private fun nextPage() {
        if (_page != LAST_PAGE) _page++
    }

    fun deletePost(id: Long) {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext) {
                kotlin.runCatching {
                    repository.deleteExamInfo(id)
                    _items.postValue(_items.value?.filterNot { examInfo -> examInfo.id == id })
                }.onFailure {
                    Log.d(TAG, "MyExamInfoViewModel - deletePost() called FAILED: ${it.message}")
                    _resultFlow.emit(Result.Fail)
                }
            }
        }
    }

    fun editButtonClickEvent(data: LectureExamDto) {
        event(Event.EditEvent(data))
    }

    fun deleteButtonClickEvent(data: LectureExamDto) {
        event(Event.DeleteEvent(data))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
