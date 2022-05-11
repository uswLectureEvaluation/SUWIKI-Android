package com.kunize.uswtimetable.ui.user_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.FindIdRepository
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.launch

class FindIdViewModel(private val repository: FindIdRepository): ViewModel() {
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    fun findId() {
        if (email.value?.isEmpty() == true) return
        val emailWithDomain = email.value + SCHOOL_DOMAIN_AT
        Log.d(TAG, "FindIdViewModel - findId(${emailWithDomain}) called")
        viewModelScope.launch {
            val response = repository.findId(emailWithDomain)
            if (response.isSuccessful && response.body()?.success == true) {
                successMessage.postValue("$emailWithDomain 로 아이디가 전송되었습니다.")
            } else {
                errorMessage.postValue("${response.code()}: ${response.message()}")
            }
        }
    }
}