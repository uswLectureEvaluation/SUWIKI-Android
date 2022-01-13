package com.kunize.uswtimetable.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.Result
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.TAG

class SignUpViewModel(private val certificateEmail: CertificateEmail): ViewModel() {

    private var _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> get() = _signupForm

    private var _certResult = MutableLiveData<Boolean>()
    val certResult: LiveData<Boolean> get() = _certResult

    val schoolMailUrl = "http://mail.suwon.ac.kr"

    val alphaNumPattern = """^[a-z0-9]*$"""
//    val passwordPattern = "^(?=.[a-zA-Z])(?=.[!@#\$%^+=-])(?=.[0-9])$"
    val passwordPattern = """/^(?=.[a-zA-Z])(?=.[!@#\$%^+=-])(?=.[0-9])$/"""
/*
    fun setPwSame() {
        _isPwSame.value = PasswordAgain.SAME
    }
    fun setPwDifferent() {
        _isPwSame.value = PasswordAgain.DIFFERENT
    }
    fun setPwEmpty() {
        _isPwSame.value = PasswordAgain.EMPTY
    }*/
    fun signup(id: String, pw: String, pwAgain: String, term: Boolean) {
        // TODO 회원 가입 로직
    }

    fun signUpDataChanged(id: String, pw: String, pwAgain: String, term: Boolean) {
        when {
            isIdValid(id).not() -> {
                _signupForm.value = SignUpFormState(idError = R.string.invalid_id)
            }
            isPwValid(pw).not() -> {
                _signupForm.value = SignUpFormState(pwError = R.string.invalid_pw)
            }
            isPwAgainValid(pw, pwAgain).not() -> {
                _signupForm.value = SignUpFormState(pwAgainError = R.string.invalid_pw_again)
            }
            term.not() -> {
                _signupForm.value = SignUpFormState(isTermChecked = R.string.unchecked_term)
            }
            else -> {
                _signupForm.value = SignUpFormState(isDataValid = true)
            }
        }
    }

    fun emailCheck(email: String) {
        // TODO 인증 로직
        when (val result = certificateEmail.certificate(email)) {
            is Result.Success -> {
                _certResult.value = result.data.success
            }
            is Result.Fail -> {
                Log.d(TAG, "SignUpViewModel - emailCheck() called / Result Fail: ${result.message}")
            }
            else -> {
                Log.d(TAG, "SignUpViewModel - emailCheck() called / Error!")
            }
        }
    }

    private fun isIdValid(id: String): Boolean {
        return id.length in ID_COUNT_LOWER_LIMIT..ID_COUNT_LIMIT
    }

    private fun isPwValid(pw: String): Boolean {
        return pw.length in PW_COUNT_LIMIT..PW_COUNT_LIMIT
    }

    private fun isPwAgainValid(pw: String, pwAgain: String): Boolean {
        return pw == pwAgain
    }
}