package com.kunize.uswtimetable.base

import androidx.fragment.app.Fragment
import com.kunize.uswtimetable.ui.common.UiEvent
import com.kunize.uswtimetable.ui.common.UiState

abstract class BaseFragment<STATE : UiState, EVENT : UiEvent> : Fragment(), ErrorHandler by CommonErrorHandler() {
    abstract fun handleEvent(event: EVENT)
    abstract fun render(state: STATE)
}
