package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.repository.signup.SignUpRepository
import com.kunize.uswtimetable.util.Constants
import kotlinx.coroutines.*
import java.util.regex.Pattern

class SignUpPage1ViewModel(private val repository: SignUpRepository): ViewModel() {
    var job: Job? = null
    val errorMessage = MutableLiveData("")
    val loading = MutableLiveData<Boolean>()
    val nextButtonEnable = MutableLiveData(false)
    val isIdUnique = MutableLiveData(false)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private var _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> get() = _signupForm

    private var _id: String? = null
    private var _pw: String? = null

    private val idPattern: Pattern = Pattern.compile(Constants.ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(Constants.PW_REGEX)

    init {
        onResume()
    }

    suspend fun checkId() {
        _id?:return
        nextButtonEnable.value = false
        onError("")
        CoroutineScope(Dispatchers.IO).async {  }
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.checkId(_id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    isIdUnique.postValue(response.body()?.overlap == false)
                    if (response.body()?.overlap == true) onError("아이디가 중복되었습니다")
                } else {
                    onError("${response.code()} Error: ${response.message()}")
                }
                nextButtonEnable.postValue(signupFormState.value?.isDataValid==true)
            }
        }

        /*viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repository.checkId(_id!!)
                when(result) {
                    is Result.Success -> {
                        result
                    }
                    is Result.Error -> {
                        result
                    }
                }
            }

        }*/
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
        nextButtonEnable.value = signupFormState.value?.isDataValid==true
    }

    fun onResume() {
        nextButtonEnable.value = signupFormState.value?.isDataValid==true
        isIdUnique.value = null
    }

    private fun isIdValid(id: String): Boolean {
        return id.isBlank() || idPattern.matcher(id).matches()
    }

    private fun checkIdLength(id: String): Boolean {
        return id.isBlank() || id.length in Constants.ID_COUNT_LOWER_LIMIT..Constants.ID_COUNT_LIMIT
    }

    private fun isPwValid(pw: String): Boolean {
        return pw.isBlank() || pwPattern.matcher(pw).matches()
    }

    private fun checkPwLength(pw: String): Boolean {
        return pw.isBlank() || pw.length in Constants.PW_COUNT_LOWER_LIMIT..Constants.PW_COUNT_LIMIT
    }

    private fun isPwAgainValid(pw: String, pwAgain: String): Boolean {
        return pw.isBlank() || pwAgain.isBlank() || pw == pwAgain
    }

    private fun hasBlank(
        id: String,
        pw: String,
        pwAgain: String
    ): Boolean {
        return id.isBlank() || pw.isBlank() || pwAgain.isBlank()
    }

    fun saveUserIdAndPw(id: String, pw: String) {
        _id = id
        _pw = pw
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}