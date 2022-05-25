package com.kunize.uswtimetable.ui.select_open_major

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.kunize.uswtimetable.repository.open_major.OpenMajorRepository
import com.kunize.uswtimetable.ui.common.Event
import kotlinx.coroutines.launch
import org.json.JSONObject

class SelectOpenMajorViewModel(private val openMajorRepository: OpenMajorRepository) : ViewModel() {
    private val _starClickEvent = MutableLiveData<Event<String>>()
    val starClickEvent: LiveData<Event<String>>
        get() = _starClickEvent

    private val _showNeedLoginLayout = MutableLiveData<Boolean>()
    val showNeedLoginLayout: LiveData<Boolean>
        get() = _showNeedLoginLayout

    val _showNoSearchResultText = MutableLiveData<String>()
    val showNoSearchResultText: LiveData<String>
        get() = _showNoSearchResultText

    init {
        _showNoSearchResultText.value = ""
    }

    fun starClick(data: String) {
        _starClickEvent.value = Event(data)
    }

    suspend fun bookmarkMajor(majorName: String) {
        viewModelScope.launch {
            openMajorRepository.bookmarkMajor(majorName)
        }
    }

    suspend fun clearBookmarkMajor(majorName: String) {
        viewModelScope.launch {
            openMajorRepository.clearBookmarkMajor(majorName)
        }
    }

    suspend fun getBookmarkList(): List<String> {
        var bookmarkList = listOf<String>()
        val response = openMajorRepository.getBookmarkMajorList()
        if (response.isSuccessful) bookmarkList = response.body()!!.data
        return bookmarkList
    }
}