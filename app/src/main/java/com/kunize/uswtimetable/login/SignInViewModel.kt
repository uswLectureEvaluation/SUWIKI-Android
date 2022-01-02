package com.kunize.uswtimetable.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.util.Constants.PasswordAgain

class SignInViewModel: ViewModel() {
    private var _isPwSame = MutableLiveData<PasswordAgain>()
    val isPwSame: LiveData<PasswordAgain> get() = _isPwSame

    val schoolMailUrl = "http://mail.suwon.ac.kr"

    fun setPwSame() {
        _isPwSame.value = PasswordAgain.SAME
    }
    fun setPwDifferent() {
        _isPwSame.value = PasswordAgain.DIFFERENT
    }
    fun setPwEmpty() {
        _isPwSame.value = PasswordAgain.EMPTY
    }
}