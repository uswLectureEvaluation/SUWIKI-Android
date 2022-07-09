package com.kunize.uswtimetable.ui.mypage.quit

sealed class Event {
    object NavigateBackEvent : Event()
    object QuitButtonEvent : Event()
    data class Result(val success: Boolean) : Event()
}
