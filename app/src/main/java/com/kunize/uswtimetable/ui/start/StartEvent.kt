package com.kunize.uswtimetable.ui.start

sealed interface StartEvent {
    object GotoMain : StartEvent
}
