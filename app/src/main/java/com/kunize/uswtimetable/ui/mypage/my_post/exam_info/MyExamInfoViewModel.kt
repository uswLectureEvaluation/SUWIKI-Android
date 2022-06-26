package com.kunize.uswtimetable.ui.mypage.my_post.exam_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.data.remote.LectureExamDto
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.LAST_PAGE
import com.kunize.uswtimetable.util.LIST_CONFIG.ONCE_REQUEST_SIZE
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyExamInfoViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _myExamInfoData = MutableLiveData<List<LectureExamDto>>()
    val myExamInfoData: LiveData<List<LectureExamDto>> get() = _myExamInfoData
    val loading = MutableLiveData<Boolean>()

    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val page = MutableLiveData<Int>()
    private var loadFinished = false

    init {
        page.value = 1
        scrollBottomEvent()
    }

    fun editButtonClickEvent(data: LectureExamDto) {
        event(Event.EditEvent(data))
    }

    fun deleteButtonClickEvent(data: LectureExamDto) {
        event(Event.DeleteEvent(data))
    }

    fun scrollBottomEvent() {
        if (loadFinished || page.value == LAST_PAGE) return
        Log.d(Constants.TAG, "MyEvaluationViewModel - scrollBottomEvent(${page.value}) called / loadFinished : $loadFinished")

        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.getExamInfos(page.value ?: 1)
            if (response.isSuccessful) {
                val newData = response.body()?.data ?: return@launch

                val currentData = _myExamInfoData.value?.toMutableList() ?: mutableListOf()
                currentData.addAll(newData)
                _myExamInfoData.postValue(currentData)

                if (newData.size < ONCE_REQUEST_SIZE) loadFinished = true
                else nextPage()
            }
            loading.postValue(false)
        }
    }

    private fun nextPage() {
        if (page.value == LAST_PAGE) return
        page.postValue(page.value?.plus(1))
    }

    fun deletePost(id: Long) {
        viewModelScope.launch {
            repository.deleteExamInfo(id)
            initList()
        }
    }

    private fun initList() {
        loadFinished = false
        _myExamInfoData.value = emptyList()
        page.value = 1
        scrollBottomEvent()
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
