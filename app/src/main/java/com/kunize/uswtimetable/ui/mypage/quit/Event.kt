package com.kunize.uswtimetable.ui.mypage.quit

sealed class Event {
    object NavigateBackEvent : Event()
    data class Result(val success: Boolean) : Event()
}
