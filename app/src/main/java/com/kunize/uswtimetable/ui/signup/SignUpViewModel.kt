package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import com.kunize.uswtimetable.ui.repository.signup.SignUpRepository
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val pwAgain = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val termChecked = MutableLiveData<Boolean>()

    val nextButtonEnable = MutableLiveData(false)
    val signUpResult = MutableLiveData<SuccessCheckDto>()

    private var _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> get() = _currentPage

    // ADDED FROM VIEWMODEL 1
    val errorMessage = MutableLiveData("")
    val loading = MutableLiveData<Boolean>()

    val isIdUnique = MutableLiveData(false)
    val isEmailUnique = MutableLiveData(false)

    private var _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> get() = _signupForm

    private val idPattern: Pattern = Pattern.compile(Constants.ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(Constants.PW_REGEX)
    // END


    init {
        _currentPage.value = 0
        loading.value = false
        setButtonAvailable()
    }

    fun moveToNextPage() {
        _currentPage.value = when (currentPage.value) {
            0 -> {
                _currentPage.value?.plus(1)
            }
            1 -> {
                _currentPage.value?.plus(1)
            }
            else -> _currentPage.value
        }
        setButtonAvailable()
    }

    fun moveToPreviousPage() {
        when (currentPage.value) {
            1 -> {
                isIdUnique.value = false
                _currentPage.value?.minus(1)
            }
            2 -> {
                isEmailUnique.value = false
                _currentPage.value?.minus(1)
            }
        }
        setButtonAvailable()
    }

    private fun setButtonAvailable() {
        nextButtonEnable.value = when (currentPage.value) {
            0 -> { // 아이디, 비밀번호, 비밀번호 다시, 약관 동의
                (loading.value == false) && (signupFormState.value?.isDataValid == true)
            }
            1 -> { // 이메일
                (loading.value == false) && (isIdUnique.value == true) && (email.value?.isNotBlank() == true)
            }
            else -> false
        }
    }

    /*fun movePage(page: Int) {
        if (page in 0..2) _currentPage.value = page
        setButtonAvailable()
    }*/

    fun checkId() {
        val userId = id.value ?: return
        loading.value = true
        viewModelScope.launch {
            val response = repository.checkId(userId)
            if (response.isSuccessful) {
                isIdUnique.postValue(response.body()?.overlap == false)
                if (response.body()?.overlap == true) {
                    onError("이미 가입된 아이디입니다.")
                }
            } else {
                onError("${response.code()} Error: ${response.errorBody()}")
            }
            loading.postValue(false)
        }
    }

    fun checkEmail() {
        val userEmail = email.value?.plus(SCHOOL_DOMAIN_AT) ?: return
        loading.value = true
        viewModelScope.launch {
            val response = repository.checkEmail(userEmail)
            if (response.isSuccessful) {
                isEmailUnique.postValue(response.body()?.overlap == false)
                if (response.body()?.overlap == true) {
                    onError("이미 가입된 이메일입니다.")
                }
            } else {
                onError("${response.code()} Error: ${response.errorBody()}")
            }
            loading.postValue(false)
        }
    }
    
    fun signUp() {
        val userId = id.value ?: return
        val userPw = pw.value ?: return
        val userEmail = email.value?.plus(SCHOOL_DOMAIN_AT) ?: return
        if (isIdUnique.value != true) {
            onError("이미 가입된 아이디입니다.")
            return
        }
        if (isEmailUnique.value != true) {
            onError("이미 가입된 이메일입니다.")
            return
        }
        viewModelScope.launch {
            val response = repository.signUp(userId, userPw, userEmail)
            if (response.isSuccessful) {
                if (response.body()?.success == true) {
                    // TODO 회원 가입 성공
                    signUpResult.postValue(response.body())
                } else {
                    signUpResult.postValue(response.body())
                    onError("회원 가입 실패 : ${response.message()}")
                }
            } else {
                signUpResult.postValue(response.body())
                onError("${response.code()} Error: ${response.errorBody()}")
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
        setButtonAvailable()
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

    private fun onError(message: String) {
        errorMessage.value = message
    }

}