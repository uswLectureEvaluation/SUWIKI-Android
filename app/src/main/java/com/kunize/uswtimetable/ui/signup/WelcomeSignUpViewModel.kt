package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.ui.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeSignUpViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _eventLoginClicked = MutableLiveData<Event<Boolean>>()
    val eventLoginClicked: LiveData<Event<Boolean>> get() = _eventLoginClicked

    private val _eventCheckEmailClicked = MutableLiveData<Event<Boolean>>()
    val eventCheckEmailClicked: LiveData<Event<Boolean>> get() = _eventCheckEmailClicked

    fun setEmail(email: String) {
        _email.value = email
    }

    fun onClickCheckMail() {
        _eventCheckEmailClicked.value = Event(true)
    }

    fun onClickLogin() {
        _eventLoginClicked.value = Event(true)
    }
}
