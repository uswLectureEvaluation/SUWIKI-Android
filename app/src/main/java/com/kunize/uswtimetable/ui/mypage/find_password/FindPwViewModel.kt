package com.kunize.uswtimetable.ui.mypage.find_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.FindPwRepository
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch

class FindPwViewModel(private val repository: FindPwRepository) : ViewModel() {
    val email = MutableLiveData<String>()
    val userId = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun findPw() {
        if (email.value.isNullOrEmpty() || userId.value.isNullOrEmpty()) return
        val email = email.value!! + SCHOOL_DOMAIN_AT
        val id = userId.value!!

        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.findPw(id, email)

            response.onSuccess {
                successMessage.postValue("${email}로 임시 비밀번호가 전송되었습니다")
            }.onError {
                when (statusCode) {
                    StatusCode.BadRequest -> errorMessage.postValue("존재하지 않는 정보입니다")
                    else -> errorMessage.postValue("알 수 없는 오류가 발생했습니다")
                }
            }

            loading.postValue(false)
        }
    }
}
