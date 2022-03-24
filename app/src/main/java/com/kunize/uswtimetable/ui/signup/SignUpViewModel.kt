package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import com.kunize.uswtimetable.ui.repository.signup.SignUpRepository
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_REGEX
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_REGEX
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import kotlinx.coroutines.*
import java.util.regex.Pattern

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    var job: Job? = null
    val errorMessage1 = MutableLiveData<String>()
    val errorMessage2 = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val nextButtonEnable = MutableLiveData(false)
    val previousButtonEnable = MutableLiveData(false)
    val isIdUnique = MutableLiveData(false)
    val isEmailUnique = MutableLiveData(false)
    val signUpResult = MutableLiveData<SuccessCheckDto>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError1("Exception handled: ${throwable.localizedMessage}")
    }

    private var _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> get() = _signupForm

    private var _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> get() = _currentPage

    private var _id: String? = null
    private var _pw: String? = null
    private var _email: String? = null
    val email get() = _email + SCHOOL_DOMAIN_AT

    private val idPattern: Pattern = Pattern.compile(ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(PW_REGEX)

    init {
        _currentPage.value = 0
    }

    fun checkId() {
        _id?:return
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            delay(500)
            val response = repository.checkId(_id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    isIdUnique.postValue(response.body()?.overlap == false)
                    if (response.body()?.overlap == true) onError1("아이디가 중복되었습니다")
                } else {
                    onError1("${response.code()} Error: ${response.message()}")
                }
            }
        }
    }

    fun checkEmail() {
        _email?:return
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            delay(500)
            val response = repository.checkEmail(email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val overlap = response.body()?.overlap
                    if (overlap==false) {
                        isEmailUnique.postValue(overlap==false)
                    } else {
                        onError2("이미 가입된 이메일입니다.")
                    }
                } else {
                    onError2("${response.code()} Error: ${response.message()}")
                }
            }
        }
    }

    fun signUp() {
        _id?:return
        _pw?:return
        _email?:return
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.signUp(_id!!, _pw!!, email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    signUpResult.postValue(response.body())
                } else {
                    onError2("${response.code()} Error: ${response.message()}")
                }
            }
        }
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

    private fun hasBlank(
        id: String,
        pw: String,
        pwAgain: String
    ): Boolean {
        return id.isBlank() || pw.isBlank() || pwAgain.isBlank()
    }

    fun moveToNextPage() {
        _currentPage.value?.let { page ->
            resetUniqueCheck(page + 1)
            if (page < 2) {
                _currentPage.value = page + 1
            }
        }
    }

    fun moveToPreviousPage() {
        _currentPage.value?.let { page ->
            resetUniqueCheck(page - 1)
            if (page > 0) {
                _currentPage.value = page - 1
            }
        }
    }

    fun movePage(page: Int) {
        resetUniqueCheck(page)
        if (page in 0..2) _currentPage.value = page
    }

    fun saveUserIdAndPw(id: String, pw: String) {
        _id = id
        _pw = pw
    }

    fun setEmail(email: String) {
        _email = email
    }

    private fun resetUniqueCheck(page: Int) {
        when (page) {
            0 -> isIdUnique.value = false
            1 -> isEmailUnique.value = false
            else -> {}
        }
    }

    enum class SignUpState {
        DEFAULT,
        INVALID_ID,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        SUCCESS,
        ERROR
    }

    private fun onError1(message: String) {
        errorMessage1.value = message
        loading.value = false
    }

    private fun onError2(message: String) {
        errorMessage2.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}