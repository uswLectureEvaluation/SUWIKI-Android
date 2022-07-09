package com.kunize.uswtimetable.ui.mypage.quit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.repository.user_info.QuitRepository
import com.kunize.uswtimetable.ui.common.User
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
                setSuccess(isSuccess = true)
                User.logout()
            } else {
                setSuccess(isSuccess = false)
            }
            loading.postValue(false)
        }
    }

    fun quitButtonClickEvent() {
        event(Event.QuitButtonEvent)
    }

    fun navigateBackEvent() {
        event(Event.NavigateBackEvent)
    }

    private fun setSuccess(isSuccess: Boolean) {
        event(Event.Result(isSuccess))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
