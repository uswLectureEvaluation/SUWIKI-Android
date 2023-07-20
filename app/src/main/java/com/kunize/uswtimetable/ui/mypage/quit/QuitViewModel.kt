package com.kunize.uswtimetable.ui.mypage.quit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.QuitRepository
import com.suwiki.domain.usecase.LogoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuitViewModel @Inject constructor(
    private val repository: QuitRepository,
    private val logoutUsecase: LogoutUsecase,
) : ViewModel() {
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
            when (val response = repository.quit(id, pw)) {
                is Result.Success -> {
                    if (response.data) {
                        setSuccess(true)
                        logoutUsecase()
                    }
                }

                is Result.Failure -> setSuccess(false)
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
