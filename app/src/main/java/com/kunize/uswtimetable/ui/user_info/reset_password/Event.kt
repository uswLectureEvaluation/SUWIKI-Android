package com.kunize.uswtimetable.ui.user_info.reset_password

sealed class Event {
    object FindPasswordEvent: Event()
    object NavigateBackEvent: Event()
}
