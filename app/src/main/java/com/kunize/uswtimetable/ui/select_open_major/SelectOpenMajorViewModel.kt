package com.kunize.uswtimetable.ui.select_open_major

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.data.local.OpenMajorItem
import com.kunize.uswtimetable.repository.start.OpenMajorRepository
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.Event

class SelectOpenMajorViewModel(private val openMajorRepository: OpenMajorRepository) : ViewModel() {
    private val _starClickEvent = MutableLiveData<Event<String>>()
    val starClickEvent: LiveData<Event<String>>
        get() = _starClickEvent

    fun starClick(data: String) {
        _starClickEvent.value = Event(data)
    }
}