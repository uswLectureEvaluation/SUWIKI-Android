package com.kunize.uswtimetable.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.repository.login.LoginRepository
import com.kunize.uswtimetable.ui.common.User
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_REGEX
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_REGEX
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val loading = MutableLiveData(false)

    val userId = MutableLiveData<String>()
    val userPw = MutableLiveData<String>()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> get() = _loginResult

    private val idPattern: Pattern = Pattern.compile(ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(PW_REGEX)

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun login(id: String, pw: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            // 로그인 API
            val loginResult = loginRepository.login(id, pw)
            if (loginResult.isSuccessful) {
                when (loginResult.code()) {
                    200 -> {
                        User.login()
                        _loginResult.postValue(LoginState.SUCCESS)
                    }
                    else -> {
                        _loginResult.postValue(LoginState.FAIL)
                        Log.d(TAG, "LoginViewModel - login() failed : ${loginResult.code()} ${loginResult.message()}")
                    }
                }
            } else {
                _loginResult.postValue(LoginState.FAIL)
            }
            loading.postValue(false)
        }
    }

    fun loginDataChanged() {
        val id = userId.value ?: return
        val pw = userPw.value ?: return

        when {
            checkIdLength(id).not() -> {
                _loginForm.value = LoginFormState(idError = R.string.check_id_length)
            }
            isIdValid(id).not() -> {
                _loginForm.value = LoginFormState(idError = R.string.invalid_id)
            }
            checkPwLength(pw).not() -> {
                _loginForm.value = LoginFormState(pwError = R.string.check_pw_length)
            }
            isPwValid(pw).not() -> {
                _loginForm.value = LoginFormState(pwError = R.string.invalid_pw)
            }
            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }

    private fun checkIdLength(id: String) =
        id.isBlank() || id.length in ID_COUNT_LOWER_LIMIT..ID_COUNT_LIMIT

    private fun isIdValid(id: String) = id.isBlank() || idPattern.matcher(id).matches()

    private fun checkPwLength(pw: String) =
        pw.isBlank() || pw.length in PW_COUNT_LOWER_LIMIT..PW_COUNT_LIMIT

    private fun isPwValid(pw: String) = pw.isBlank() || pwPattern.matcher(pw).matches()

    fun findIdEvent() {
        event(Event.FindId(Unit))
    }

    fun findPwEvent() {
        event(Event.FindPw(Unit))
    }

    fun signUpEvent() {
        event(Event.SignUp(Unit))
    }

    fun rememberCheckEvent(checked: Boolean) {
        event(Event.CheckRemember(checked))
    }

    private fun event(event: Event) {
        viewModelScope.launch { _eventFlow.emit(event) }
    }

    sealed class Event {
        data class FindId(val p: Unit): Event()
        data class FindPw(val p: Unit): Event()
        data class SignUp(val p: Unit): Event()
        data class CheckRemember(val checked: Boolean): Event()
    }
}

enum class LoginState {
    SUCCESS,
    FAIL
}
