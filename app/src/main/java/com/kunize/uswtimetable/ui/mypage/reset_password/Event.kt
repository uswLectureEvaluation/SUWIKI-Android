package com.kunize.uswtimetable.ui.mypage.reset_password

sealed class Event {
    object FindPasswordEvent: Event()
    object NavigateBackEvent: Event()
}
