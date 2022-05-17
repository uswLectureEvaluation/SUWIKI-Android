package com.kunize.uswtimetable.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.MyExamInfoDto
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.ItemType
import com.kunize.uswtimetable.util.LAST_PAGE
import com.kunize.uswtimetable.util.LIST_CONFIG.ONCE_REQUEST_SIZE
import kotlinx.coroutines.launch

class MyExamInfoViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _myExamInfoData = MutableLiveData<List<MyExamInfoDto>>()
    val myExamInfoData: LiveData<List<MyExamInfoDto>> get() = _myExamInfoData
    val loading = MutableLiveData<Boolean>()
    private val _eventClicked = MutableLiveData<Event<Pair<ItemType, MyExamInfoDto>>>()
    val eventClicked: LiveData<Event<Pair<ItemType, MyExamInfoDto>>> get() = _eventClicked
    private val page = MutableLiveData<Int>()
    private var loadFinished = false

    init {
        page.value = 1
        scrollBottomEvent()
    }

    fun onItemClicked(type: ItemType, data: MyExamInfoDto) {
        _eventClicked.value = Event(type to data)
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
}