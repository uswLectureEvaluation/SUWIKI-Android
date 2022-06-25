package com.kunize.uswtimetable.ui.user_info.quit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.QuitRepository
import com.kunize.uswtimetable.ui.user_info.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class QuitViewModel(private val repository: QuitRepository) : ViewModel() {
    val loginId = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun quit() {
        if (loginId.value?.isEmpty() == true || password.value?.isEmpty() == true) return
        val id = loginId.value ?: return
        val pw = password.value ?: return
        loading.value = true
        viewModelScope.launch {
            val response = repository.quit(id, pw)
            if (response.isSuccessful && response.body()?.success == true) {
                setSuccessMessage("성공적으로 탈퇴처리 되었습니다.")
                User.logout()
            } else {
                setErrorMessage("${response.code()}: ${response.message()}")
            }
            loading.postValue(false)
        }
    }

    fun navigateBackEvent() {
        event(Event.NavigateBackEvent)
    }

    private fun setSuccessMessage(message: String) {
        event(Event.SuccessMessage(message))
    }

    private fun setErrorMessage(message: String) {
        event(Event.ErrorMessage(message))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}