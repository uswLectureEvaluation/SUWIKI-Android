package com.kunize.uswtimetable.ui.start

import androidx.annotation.StringRes
import com.kunize.uswtimetable.R

data class StartState(
    @StringRes val progressText: Int = R.string.start_version_checking,
    val progressPercent: Int = 0,
)
