package com.kunize.uswtimetable.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.LoggedInUserView
import com.kunize.uswtimetable.dataclass.LoginRepository
import com.kunize.uswtimetable.dataclass.Result

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult

    fun login(id: String, pw: String) {
        val result = loginRepository.login(id, pw)

        if (result is Result.Success) {
            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_fail)
        }
    }

    fun loginDataChanged(id: String, pw: String) {
        when {
            isIdValid(id).not() -> {
                _loginForm.value = LoginFormState(idError = R.string.invalid_id)
            }
            isPwValid(pw).not() -> {
                _loginForm.value = LoginFormState(pwError = R.string.invalid_pw)
            }
            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }

    private fun isIdValid(id: String): Boolean {
        return id.isNotBlank()
    }

    private fun isPwValid(pw: String): Boolean {
        return pw.length > 5
    }
}