package com.kunize.uswtimetable.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.dataclass.UserDataDto
import com.kunize.uswtimetable.ui.repository.login.LoginRepository
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_REGEX
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_REGEX
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private var job: Job? = null
    val loading = MutableLiveData(false)

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> get() = _loginResult

    private val idPattern: Pattern = Pattern.compile(ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(PW_REGEX)

    fun login(id: String, pw: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            // 로그인 API
            val loginResult = loginRepository.login(id, pw)
            if (loginResult.isSuccessful) {
                when (loginResult.code()) {
                    200 -> {
                        // 내 정보 요청 API
                        delay(500)
                        val userDataResult = loginRepository.getUserData()
                        if (userDataResult.isSuccessful) {
                            if (userDataResult.code() == 200) {
                                Log.d(
                                    TAG,
                                    "LoginViewModel - userData: ${userDataResult.body()}"
                                )
                                setUserData(userDataResult.body()!!)
                                _loginResult.postValue(LoginState.SUCCESS)
                            }
                        } else {
                            Log.d(TAG, "내 정보 API 요청 실패: ${userDataResult.code()}")
                            Log.d(
                                TAG,
                                "errorBody: ${userDataResult.errorBody()} message: ${userDataResult.message()} raw: ${userDataResult.raw()} body: ${userDataResult.body()}"
                            )
                        }
                    }
                    else -> {
                        Log.d(
                            TAG,
                            "LoginViewModel - login() failed / ${loginResult.code()}: ${loginResult.message()}"
                        )
                        _loginResult.postValue(LoginState.FAIL)
                    }
                }
            } else {
                _loginResult.postValue(LoginState.FAIL)
            }
            loading.postValue(false)
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

    private fun checkIdLength(id: String) =
        id.isBlank() || id.length in ID_COUNT_LOWER_LIMIT..ID_COUNT_LIMIT

    private fun isIdValid(id: String) = id.isBlank() || idPattern.matcher(id).matches()

    private fun checkPwLength(pw: String) =
        pw.isBlank() || pw.length in PW_COUNT_LOWER_LIMIT..PW_COUNT_LIMIT

    private fun isPwValid(pw: String) = pw.isBlank() || pwPattern.matcher(pw).matches()

    private fun setUserData(userData: UserDataDto) {
        User.setUser(
            LoggedInUser(
                userId = userData.userId,
                point = userData.point,
                writtenExam = userData.writtenExam,
                writtenLecture = userData.writtenEvaluation,
                viewExam = userData.viewExam
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

enum class LoginState {
    SUCCESS,
    FAIL
}
