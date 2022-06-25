package com.kunize.uswtimetable.ui.user_info.quit

sealed class Event {
    object NavigateBackEvent: Event()
    data class SuccessMessage(val message: String): Event()
    data class ErrorMessage(val message: String): Event()
}