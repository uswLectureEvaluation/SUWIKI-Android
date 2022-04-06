package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ToastViewModel: ViewModel() {
    protected val _makeToast = MutableLiveData<Event<Boolean>>()
    val makeToast: LiveData<Event<Boolean>> = _makeToast
    var toastMessage: String = ""
}