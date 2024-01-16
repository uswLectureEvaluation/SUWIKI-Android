package com.suwiki.feature.timetable.timetable

import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor

data class TimetableState(
  val timetable: Timetable? = null,
  val selectedCell: TimetableCell = TimetableCell(color = TimetableCellColor.GRAY_DARK),
  val showEditCellBottomSheet: Boolean = false,
  val showTimetableEmptyColumn: Boolean? = null,
)

sealed interface TimetableSideEffect {
  data object ShowNeedCreateTimetableToast : TimetableSideEffect
  data object NavigateAddTimetableCell : TimetableSideEffect
  data object NavigateCreateTimetable : TimetableSideEffect
  data class HandleException(val throwable: Throwable) : TimetableSideEffect
}
