package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loginEvent() {
        event(Event.UiEvent(Type.BTN_LOGIN))
    }

    fun myPostEvent() {
        event(Event.UiEvent(Type.BTN_MY_POST))
    }

    fun noticeEvent() {
        event(Event.UiEvent(Type.MENU_NOTICE))
    }

    fun feedbackEvent() {
        event(Event.UiEvent(Type.MENU_FEEDBACK))
    }

    fun questionEvent() {
        event(Event.UiEvent(Type.MENU_QUESTION))
    }

    fun changePwEvent() {
        event(Event.UiEvent(Type.MENU_CHANGE_PW))
    }

    fun termsEvent() {
        event(Event.UiEvent(Type.MENU_TERMS))
    }

    fun privatePolicyEvent() {
        event(Event.UiEvent(Type.MENU_PRIVACY_POLICY))
    }

    fun signOutEvent() {
        event(Event.UiEvent(Type.MENU_SIGN_OUT))
    }

    fun logoutEvent() {
        event(Event.UiEvent(Type.BTN_LOGOUT))
    }

    fun findIdEvent() {
        event(Event.UiEvent(Type.MENU_FIND_ID))
    }

    fun findPwEvent() {
        event(Event.UiEvent(Type.MENU_FIND_PW))
    }

    fun openSourceEvent() {
        event(Event.UiEvent(Type.MENU_OPENSOURCE))
    }

    fun limitHistoryEvent() {
        event(Event.UiEvent(Type.MENU_LIMIT_HISTORY))
    }

    fun purchaseHistoryEvent() {
        event(Event.UiEvent(Type.MENU_PURCHASE_HISTORY))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class UiEvent(val type: Type): Event()
    }

    enum class Type {
        BTN_LOGIN,
        BTN_LOGOUT,
        BTN_MY_POST,
        MENU_LIMIT_HISTORY,
        MENU_PURCHASE_HISTORY,
        MENU_NOTICE,
        MENU_FIND_ID,
        MENU_FIND_PW,
        MENU_FEEDBACK,
        MENU_QUESTION,
        MENU_CHANGE_PW,
        MENU_TERMS,
        MENU_PRIVACY_POLICY,
        MENU_SIGN_OUT,
        MENU_OPENSOURCE
    }
}