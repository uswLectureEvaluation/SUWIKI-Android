package com.kunize.uswtimetable.ui.user_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.repository.user_info.FindPwRepository
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.launch

class FindPwViewModel(private val repository: FindPwRepository): ViewModel() {
    val email = MutableLiveData<String>()
    val userId = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun findPw() {
        if (email.value.isNullOrEmpty() || userId.value.isNullOrEmpty()) return
        val email = email.value!! + SCHOOL_DOMAIN_AT
        val id = userId.value!!
        Log.d(TAG, "FindPwViewModel - findPw($id, $email) called")
        viewModelScope.launch {
            val response = repository.findPw(id, email)
            if (response.isSuccessful && response.body()?.success==true) {
                successMessage.postValue("${email}로 임시 비밀번호가 전송되었습니다")
            } else {
                errorMessage.postValue("${response.code()}: ${response.message()}")
            }
        }
    }
}