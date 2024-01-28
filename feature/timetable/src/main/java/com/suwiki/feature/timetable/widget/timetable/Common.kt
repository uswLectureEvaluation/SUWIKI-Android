package com.suwiki.feature.timetable.widget.timetable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.suwiki.core.ui.R
import com.suwiki.core.model.timetable.TimetableDay

internal val glanceTimetableHeightPerHour = 48.dp

internal val glanceTimetableBorderWidth = 0.5.dp

@Composable
internal fun TimetableDay.toGlanceText(context: Context): String {
  return when (this) {
    TimetableDay.MON -> context.getString(R.string.word_mon)
    TimetableDay.TUE -> context.getString(R.string.word_tue)
    TimetableDay.WED -> context.getString(R.string.word_wed)
    TimetableDay.THU -> context.getString(R.string.word_thu)
    TimetableDay.FRI -> context.getString(R.string.word_fri)
    TimetableDay.SAT -> context.getString(R.string.word_sat)
    TimetableDay.E_LEARNING -> context.getString(R.string.word_none)
  }
}
