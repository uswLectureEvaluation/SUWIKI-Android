package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.domain.usecase.GetUserInfoUsecase
import com.kunize.uswtimetable.domain.usecase.LogoutUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUsecase: LogoutUsecase,
    userInfoUsecase: GetUserInfoUsecase,
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    val loggedIn: StateFlow<Boolean> = userInfoUsecase.isLoggedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false,
        )

    val userInfo: StateFlow<LoggedInUser> = userInfoUsecase().transform {
        if (it != null) {
            emit(it)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        LoggedInUser(),
    )

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
        event(Event.TryLogoutEvent)
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

    fun logout() {
        viewModelScope.launch {
            logoutUsecase()
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object LoginEvent : Event()
        object TryLogoutEvent : Event()
        object MyPostEvent : Event()
        object LimitHistoryEvent : Event()
        object PurchaseHistoryEvent : Event()
        object NoticeEvent : Event()
        object FeedbackEvent : Event()
        object QuestionEvent : Event()
        object ChangePwEvent : Event()
        object TermsEvent : Event()
        object PrivacyPolicyEvent : Event()
        object SignOutEvent : Event()
        object OpenSourceEvent : Event()
    }
}
