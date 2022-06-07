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
        event(Event.LoginEvent)
    }

    fun myPostEvent() {
        event(Event.MyPostEvent)
    }

    fun noticeEvent() {
        event(Event.NoticeEvent)
    }

    fun feedbackEvent() {
        event(Event.FeedbackEvent)
    }

    fun questionEvent() {
        event(Event.QuestionEvent)
    }

    fun changePwEvent() {
        event(Event.ChangePwEvent)
    }

    fun termsEvent() {
        event(Event.TermsEvent)
    }

    fun privacyPolicyEvent() {
        event(Event.PrivacyPolicyEvent)
    }

    fun signOutEvent() {
        event(Event.SignOutEvent)
    }

    fun logoutEvent() {
        event(Event.LogoutEvent)
    }

    fun openSourceEvent() {
        event(Event.OpenSourceEvent)
    }

    fun limitHistoryEvent() {
        event(Event.LimitHistoryEvent)
    }

    fun purchaseHistoryEvent() {
        event(Event.PurchaseHistoryEvent)
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object LoginEvent : Event()
        object LogoutEvent: Event()
        object MyPostEvent: Event()
        object LimitHistoryEvent: Event()
        object PurchaseHistoryEvent: Event()
        object NoticeEvent: Event()
        object FeedbackEvent: Event()
        object QuestionEvent: Event()
        object ChangePwEvent: Event()
        object TermsEvent: Event()
        object PrivacyPolicyEvent: Event()
        object SignOutEvent: Event()
        object OpenSourceEvent: Event()
    }
}