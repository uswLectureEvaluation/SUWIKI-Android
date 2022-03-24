package com.kunize.uswtimetable.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.repository.signup.SignUpRepository
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.ID_REGEX
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LOWER_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_REGEX
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.kunize.uswtimetable.util.Constants.TAG
import java.util.regex.Pattern

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {

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
        repository.getIdCheckResult()
        repository.getEmailCheckResult()
    }

    fun signup(): LiveData<SignUpState> {
        val id = _id ?: return MutableLiveData(SignUpState.INVALID_ID)
        val pw = _pw ?: return MutableLiveData(SignUpState.INVALID_PASSWORD)
        val email = email

        repository.signUp(id, pw, email)
        return repository.getSignUpResult()
    }

    fun checkId(): LiveData<SignUpState> {
        Log.d(TAG, "SignUpViewModel - checkId() called")
        _id?.let {
            repository.checkId(it)
            return repository.getIdCheckResult()
        }
        return MutableLiveData(SignUpState.INVALID_ID)
    }

    fun checkEmail(): LiveData<SignUpState> {
        _email?.let {
            repository.checkEmail(it)
            return repository.getEmailCheckResult()
        }
        return MutableLiveData(SignUpState.INVALID_EMAIL)
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

    fun movePage(page: Int) {
        if (page in 0..2) _currentPage.value = page
    }

    fun saveUserIdAndPw(id: String, pw: String) {
        _id = id
        _pw = pw
    }

    fun setEmail(email: String) {
        _email = email
    }

    fun resetIdResult() = repository.resetIdResult()
    fun resetEmailResult() = repository.resetEmailResult()

    enum class SignUpState {
        DEFAULT,
        INVALID_ID,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        SUCCESS,
        ERROR
    }
}