package com.kunize.uswtimetable.ui.user_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.repository.user_info.QuitRepository
import kotlinx.coroutines.launch

class QuitViewModel(private val repository: QuitRepository) : ViewModel() {
    val loginId = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun quit() {
        if (loginId.value?.isEmpty() == true || password.value?.isEmpty() == true) return
        val id = loginId.value ?: return
        val pw = password.value ?: return
        loading.value = true
        viewModelScope.launch {
            val response = repository.quit(id, pw)
            if (response.isSuccessful && response.body()?.success == true) {
                successMessage.postValue("성공적으로 탈퇴처리 되었습니다. ㅠㅠ")
            } else {
                errorMessage.postValue("${response.code()}: ${response.message()}")
            }
            loading.postValue(false)
        }
    }
}