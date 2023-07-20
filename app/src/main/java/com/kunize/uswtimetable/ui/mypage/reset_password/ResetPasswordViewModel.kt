package com.kunize.uswtimetable.ui.mypage.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.ResetPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: ResetPasswordRepository,
) : ViewModel() {
    val currentPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val result = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun resetPassword() {
        val current = currentPassword.value ?: return
        val new = newPassword.value ?: return
        loading.value = true
        viewModelScope.launch {
            when (val response = repository.resetPassword(current, new)) {
                is Result.Success -> result.value = response.data
                is Result.Failure -> result.value = false
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
