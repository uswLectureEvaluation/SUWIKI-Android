package com.kunize.uswtimetable.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.util.PasswordAgain

class SignUpViewModel: ViewModel() {
    private var _isPwSame = MutableLiveData<PasswordAgain>()
    val isPwSame: LiveData<PasswordAgain> get() = _isPwSame

    val schoolMailUrl = "http://mail.suwon.ac.kr"

    val alphaNumPattern = """^[a-z0-9]*$"""
//    val passwordPattern = "^(?=.[a-zA-Z])(?=.[!@#\$%^+=-])(?=.[0-9])$"
    val passwordPattern = """/^(?=.[a-zA-Z])(?=.[!@#\$%^+=-])(?=.[0-9])$/"""

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