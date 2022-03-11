package com.kunize.uswtimetable.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.repository.LoginRepository
import com.kunize.uswtimetable.util.Result

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> get() = _loginResult

    var isLoggedIn = false
        private set

    init {
         isLoggedIn = User.isLoggedIn
    }

    fun login(id: String, pw: String) {
        val result = loginRepository.login(id, pw)

        if (result is Result.Success) {
            _loginResult.value = LoginState.SUCCESS
        } else {
            // TODO 에러 형식에 따라 분기 필요
            _loginResult.value = LoginState.UNKNOWN_ERROR
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

enum class LoginState {
    SUCCESS,
    ID_ERROR,
    PW_ERROR,
    UNKNOWN_ERROR
}