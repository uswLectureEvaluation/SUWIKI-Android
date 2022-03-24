package com.kunize.uswtimetable.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import com.kunize.uswtimetable.ui.repository.signup.SignUpRepository
import com.kunize.uswtimetable.util.Constants
import kotlinx.coroutines.*

class SignUpPage2ViewModel(private val repository: SignUpRepository): ViewModel() {
    var job: Job? = null
    val errorMessage = MutableLiveData("")
    val loading = MutableLiveData<Boolean>()
    val isEmailUnique = MutableLiveData(false)
    val signUpResult = MutableLiveData<SuccessCheckDto>()
    private var _email: String? = null
    val email get() = _email + Constants.SCHOOL_DOMAIN_AT
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private lateinit var id: String
    private lateinit var pw: String

    fun checkEmail() {
        _email?:return
        onError("")
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.checkEmail(email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val overlap = response.body()?.overlap
                    if (overlap==false) {
                        isEmailUnique.postValue(overlap==false)
                    } else {
                        onError("이미 가입된 이메일입니다.")
                    }
                } else {
                    onError("${response.code()} Error: ${response.message()}")
                }
            }
        }
    }

    fun signUp() {
        _email?:return
        onError("")
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.signUp(id, pw, email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    signUpResult.postValue(response.body())
                } else {
                    onError("${response.code()} Error: ${response.message()}")
                }
            }
        }
    }

    fun setIdPw(id: String, pw: String) {
        this.id = id
        this.pw = pw
    }

    fun setEmail(email: String) {
        _email = email
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