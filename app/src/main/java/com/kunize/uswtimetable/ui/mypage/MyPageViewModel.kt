package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.MyPageViewType

class MyPageViewModel : ViewModel() {
    private val _eventClick = MutableLiveData<Event<MyPageViewType>>()
    val eventClick: LiveData<Event<MyPageViewType>> get() = _eventClick

    fun onClick(type: MyPageViewType) {
        _eventClick.value = Event(type)
    }
}