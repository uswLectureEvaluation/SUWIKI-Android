package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToastViewModel {
    private val _toastMutableLiveData = MutableLiveData<Event<Boolean>>()
    val toastLiveData: LiveData<Event<Boolean>> = _toastMutableLiveData
    var toastMessage: String = ""

    fun showToastMsg() {
        _toastMutableLiveData.value = Event(true)
    }
}