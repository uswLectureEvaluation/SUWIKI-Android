package com.suwiki.presentation.timetable.timetable.component.timetable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.suwiki.common.model.timetable.TimetableDay
import com.suwiki.presentation.common.ui.R

internal val timetableHeightPerHour = 48.dp

internal val timetableBorderWidth = 0.5.dp

const val MINUTE60 = 60
const val MINUTE10 = 10

@Composable
internal fun TimetableDay.toText(): String {
  return when (this) {
    TimetableDay.MON -> stringResource(R.string.word_mon)
    TimetableDay.TUE -> stringResource(R.string.word_tue)
    TimetableDay.WED -> stringResource(R.string.word_wed)
    TimetableDay.THU -> stringResource(R.string.word_thu)
    TimetableDay.FRI -> stringResource(R.string.word_fri)
    TimetableDay.SAT -> stringResource(R.string.word_sat)
    TimetableDay.E_LEARNING -> stringResource(R.string.word_none)
  }
}
