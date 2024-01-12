package com.suwiki.feature.timetable.timetable

import com.suwiki.core.model.timetable.Timetable

data class TimetableState(
  val timetable: Timetable? = null,
  val showTimetableEmptyColumn: Boolean? = null,
)

sealed interface TimetableSideEffect {
  data class HandleException(val throwable: Throwable) : TimetableSideEffect
}
