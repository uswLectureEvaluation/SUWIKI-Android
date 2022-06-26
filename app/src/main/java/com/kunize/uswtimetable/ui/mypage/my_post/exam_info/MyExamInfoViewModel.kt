package com.kunize.uswtimetable.ui.mypage.my_post.exam_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.data.remote.LectureExamDto
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyExamInfoViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _items = MutableLiveData<List<LectureExamDto>>(emptyList())
    val items: LiveData<List<LectureExamDto>> get() = _items
    val loading = MutableLiveData<Boolean>()

    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var _page = 1
    private var _loadFinished = false

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
            repository.deleteExamInfo(id)
        }
        _items.value = _items.value?.filterNot { examInfo -> examInfo.id == id }
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
