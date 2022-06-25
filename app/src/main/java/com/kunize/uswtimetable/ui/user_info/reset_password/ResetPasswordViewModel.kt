package com.kunize.uswtimetable.ui.user_info.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.ResetPasswordRepository
import com.kunize.uswtimetable.util.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ResetPasswordViewModel(private val repository: ResetPasswordRepository) : ViewModel() {
    val currentPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val result = MutableLiveData<Result<Any>>()
    val loading = MutableLiveData<Boolean>()
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun resetPassword() {
        val current = currentPassword.value ?: return
        val new = newPassword.value ?: return
        loading.value = true
        viewModelScope.launch {
            val response = repository.resetPassword(current, new)
            if (response.isSuccessful && response.body()?.success == true) {
                // 비밀번호 변경 성공
                result.postValue(Result.Success(true))
            } else {
                // 비밀번호 변경 실패
                result.postValue(Result.Error(IOException("${response.code()}: ${response.message()}")))
            }
            loading.postValue(false)
        }
    }

    fun findPasswordButtonClickEvent() {
        event(Event.FindPasswordEvent)
    }

    fun navigateBackEvent() {
        event(Event.NavigateBackEvent)
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}