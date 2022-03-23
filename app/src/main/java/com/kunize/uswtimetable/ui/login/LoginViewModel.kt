package com.kunize.uswtimetable.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.repository.login.LoginRepository
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_REGEX
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_REGEX
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> get() = _loginResult

    var isLoggedIn = false
        private set

    private val idPattern: Pattern = Pattern.compile(ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(PW_REGEX)

    init {
        isLoggedIn = User.isLoggedIn
    }

    fun login(id: String, pw: String) {
        /*val result = loginRepository.login(id, pw)

        if (result is Result.Success) {
            User.setUser(result.data)
            _loginResult.value = LoginState.SUCCESS
        } else {
            // TODO 에러 형식에 따라 분기 필요
            _loginResult.value = LoginState.UNKNOWN_ERROR
        }*/

        scope.launch {
            val token = loginRepository.login(id, pw)
            Log.d(TAG, "LoginViewModel - login() called / token: $token")
        }
    }

    fun loginDataChanged(id: String, pw: String) {
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

    private fun checkIdLength(id: String) = id.isBlank() || id.length in ID_COUNT_LOWER_LIMIT..ID_COUNT_LIMIT

    private fun isIdValid(id: String) = id.isBlank() || idPattern.matcher(id).matches()

    private fun checkPwLength(pw: String) =
        pw.isBlank() || pw.length in PW_COUNT_LOWER_LIMIT..PW_COUNT_LIMIT

    private fun isPwValid(pw: String) = pw.isBlank() || pwPattern.matcher(pw).matches()

}

enum class LoginState {
    SUCCESS,
    ID_ERROR,
    PW_ERROR,
    UNKNOWN_ERROR
}