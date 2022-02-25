package com.kunize.uswtimetable.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LOWER_LIMIT
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {

    private var _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> get() = _signupForm

    private var _certResult = MutableLiveData<Boolean>()
    val certResult: LiveData<Boolean> get() = _certResult

    private var _currentPage = MutableLiveData<Int>(0)
    val currentPage: LiveData<Int> get() = _currentPage

    private var _id: String? = null
    private var _pw: String? = null
    private var _email: String? = null
    val email get() = _email

    val schoolMailUrl = "http://mail.suwon.ac.kr"

    private val idRegex = """^[a-z0-9]*$"""
    private val pwRegex = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
    private val idPattern: Pattern = Pattern.compile(idRegex)
    private val pwPattern: Pattern = Pattern.compile(pwRegex)

    fun signup(id: String, pw: String, email: String) {
        // TODO 회원 가입 로직

    }

    fun signUpDataChanged(
        id: String,
        pw: String,
        pwAgain: String,
        term: Boolean
    ) {
        when {
            checkIdLength(id).not() -> {
                _signupForm.value = SignUpFormState(idError = R.string.check_id_length)
            }
            isIdValid(id).not() -> {
                _signupForm.value = SignUpFormState(idError = R.string.invalid_id)
            }
            checkPwLength(pw).not() -> {
                _signupForm.value = SignUpFormState(pwError = R.string.check_pw_length)
            }
            isPwValid(pw).not() -> {
                _signupForm.value = SignUpFormState(pwError = R.string.invalid_pw)
            }
            isPwAgainValid(pw, pwAgain).not() -> {
                _signupForm.value = SignUpFormState(pwAgainError = R.string.invalid_pw_again)
            }
            hasBlank(id, pw, pwAgain) -> {
                _signupForm.value = SignUpFormState(hasBlank = R.string.has_blank)
            }
            term.not() -> {
                _signupForm.value = SignUpFormState(isTermChecked = R.string.unchecked_term)
            }
            else -> _signupForm.value = SignUpFormState(isDataValid = true)
        }
    }

    fun certificate(email: String) {
        // TODO 인증 로직
        /*when (val result = certificateEmail.certificate(email)) {
            is Result.Success -> {
                _certResult.value = result.data.success
            }
            is Result.Fail -> {
                Log.d(TAG, "SignUpViewModel - emailCheck() called / Result Fail: ${result.message}")
            }
            else -> {
                Log.d(TAG, "SignUpViewModel - emailCheck() called / Error!")
            }
        }*/
    }

    fun sendEmail(address: String) {
        // TODO 이메일 전송 로직
    }

    private fun isIdValid(id: String): Boolean {
        return id.isBlank() || idPattern.matcher(id).matches()
    }

    private fun checkIdLength(id: String): Boolean {
        return id.isBlank() || id.length in ID_COUNT_LOWER_LIMIT..ID_COUNT_LIMIT
    }

    private fun isPwValid(pw: String): Boolean {
        return pw.isBlank() || pwPattern.matcher(pw).matches()
    }

    private fun checkPwLength(pw: String): Boolean {
        return pw.isBlank() || pw.length in PW_COUNT_LOWER_LIMIT..PW_COUNT_LIMIT
    }

    private fun isPwAgainValid(pw: String, pwAgain: String): Boolean {
        return pw.isBlank() || pwAgain.isBlank() || pw == pwAgain
    }

    fun setEmail(email: String) {
        _email = email
    }

    private fun hasBlank(
        id: String,
        pw: String,
        pwAgain: String
    ): Boolean {
        return id.isBlank() || pw.isBlank() || pwAgain.isBlank()
    }

    fun moveToNextPage() {
        _currentPage.value?.let { page ->
            if (page < 2) {
                _currentPage.value = page + 1
            }
        }
    }

    fun moveToPreviousPage() {
        _currentPage.value?.let { page ->
            if (page > 0) {
                _currentPage.value = page - 1
            }
        }
    }
}