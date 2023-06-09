package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.repository.signup.SignUpRepository
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SignUpRepository,
) : ViewModel() {
    // User Input
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val pwAgain = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val termChecked = MutableLiveData<Boolean>()

    // Button
    val nextButtonEnable = MutableLiveData(false)
    val nextButtonText = MutableLiveData<String>()

    // State
    val loading = MutableLiveData<Boolean>()
    private var _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> get() = _currentPage
    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> get() = _toastMessage
    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> get() = _errorMessage
    private var _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> get() = _signupForm
    val idCheckButtonEnabled = MutableLiveData(false)

    // Response
    val isIdUnique = MutableLiveData(false)
    val isEmailUnique = MutableLiveData(false)
    val signUpResult = MutableLiveData<Boolean>()

    // Event
    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> get() = _success

    // Pattern
    private val idPattern: Pattern = Pattern.compile(Constants.ID_REGEX)
    private val pwPattern: Pattern = Pattern.compile(Constants.PW_REGEX)

    init {
        _currentPage.value = 0
        loading.value = false
        setButtonAttr()
        setNextButtonEnable()
    }

    fun signUpDataChanged(
        id: String,
        pw: String,
        pwAgain: String,
        term: Boolean,
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
        setNextButtonEnable()
    }

    fun onNextButtonClick() {
        when (currentPage.value) {
            0 -> if (isIdUnique.value == true) moveToNextPage()
            1 -> signUp()
            else -> throw IllegalArgumentException("Invalid sign up page: ${currentPage.value}")
        }
    }

    fun moveToPreviousPage() {
        _currentPage.value = when (currentPage.value) {
            1 -> _currentPage.value?.minus(1)
            else -> throw IllegalArgumentException("Invalid sign up page: ${currentPage.value}")
        }
        setButtonAttr()
        setNextButtonEnable()
    }

    fun setNextButtonEnable() {
        nextButtonEnable.value = when (currentPage.value) {
            0 -> (loading.value == false) && (signupFormState.value?.isDataValid == true) && (isIdUnique.value == true) // 아이디, 비밀번호, 비밀번호 다시, 약관 동의
            1 -> (loading.value == false) && (isIdUnique.value == true) && (email.value?.isNotBlank() == true) // 이메일
            else -> false
        }
    }

    fun setToastMessage(message: String) {
        _toastMessage.value = Event(message)
    }

    fun showCheckIdButton() {
        idCheckButtonEnabled.value =
            id.value?.length!! >= 6 && idPattern.matcher(id.value!!).matches()
        isIdUnique.value = false
    }

    private fun moveToNextPage() {
        _currentPage.value = when (currentPage.value) {
            0 -> _currentPage.value?.plus(1)
            1 -> _currentPage.value?.plus(1)
            else -> throw IllegalArgumentException("Invalid sign up page: ${currentPage.value}")
        }
        setButtonAttr()
        setNextButtonEnable()
    }

    private fun setButtonAttr() {
        // 페이지 변경 시에만 호출
        when (currentPage.value) {
            0 -> nextButtonText.value = "다음"
            1 -> nextButtonText.value = "회원가입"
        }
    }

    fun checkId() {
        val userId = id.value ?: return
        loading.value = true
        viewModelScope.launch {
            val response = repository.checkId(userId)
            if (response.isSuccessful) {
                isIdUnique.postValue(response.body()?.overlap == false)
                if (response.body()?.overlap == true) {
                    onError("이미 가입된 아이디입니다.")
                } else {
                    idCheckButtonEnabled.postValue(false)
                    _toastMessage.postValue(Event("사용 가능한 아이디입니다."))
                }
            } else {
                onError("${response.code()} Error: ${response.errorBody()}")
            }
            loading.postValue(false)
        }
    }

    private fun signUp() {
        val userId = id.value ?: return
        val userPw = pw.value ?: return
        val userEmail = email.value?.plus(SCHOOL_DOMAIN_AT) ?: return
        if (isIdUnique.value != true) {
            onError("이미 가입된 아이디입니다.")
            return
        }
        loading.value = true
        viewModelScope.launch {
            // 이메일 중복 확인
            val emailResponse = repository.checkEmail(userEmail)
            if (emailResponse.isSuccessful) {
                isEmailUnique.postValue(emailResponse.body()?.overlap == false)
                if (emailResponse.body()?.overlap == true) {
                    loading.postValue(false)
                    onError("이미 가입된 이메일입니다.")
                    return@launch
                }
            } else {
                onError("이메일 중복 확인 에러: ${emailResponse.errorBody()}")
                loading.postValue(false)
                return@launch
            }
            // 회원 가입
            val signUpResponse = repository.signUp(userId, userPw, userEmail)
            if (signUpResponse.isSuccessful) {
                signUpResult.postValue(signUpResponse.body()?.success == true)
                if (signUpResponse.body()?.success == true) {
                    successSignUp()
                } else {
                    onError("회원 가입 실패: ${signUpResponse.message()}")
                    loading.postValue(false)
                    return@launch
                }
            } else {
                onError("회원 가입 에러: ${signUpResponse.errorBody()}")
                loading.postValue(false)
                return@launch
            }
            loading.postValue(false)
        }
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
        pwAgain: String,
    ): Boolean {
        return id.isBlank() || pw.isBlank() || pwAgain.isBlank()
    }

    private fun onError(message: String) {
        _errorMessage.value = Event(message)
    }

    private fun successSignUp() {
        _success.value = Event(true)
    }
}
