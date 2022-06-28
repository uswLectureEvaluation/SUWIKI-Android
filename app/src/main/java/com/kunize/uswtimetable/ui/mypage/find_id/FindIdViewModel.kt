package com.kunize.uswtimetable.ui.mypage.find_id

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.FindIdRepository
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import kotlinx.coroutines.launch

class FindIdViewModel(private val repository: FindIdRepository): ViewModel() {
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>(false)

    fun findId() {
        if (email.value?.isEmpty() == true) return
        val emailWithDomain = email.value + SCHOOL_DOMAIN_AT
        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.findId(emailWithDomain)
            if (response.isSuccessful && response.body()?.success == true) {
                successMessage.postValue("$emailWithDomain 로 아이디가 전송되었습니다.")
            } else {
                errorMessage.postValue("${response.code()}: ${response.message()}")
            }
            loading.postValue(false)
        }
    }
}