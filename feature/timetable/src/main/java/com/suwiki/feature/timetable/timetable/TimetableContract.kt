package com.suwiki.feature.timetable.timetable

import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.feature.timetable.navigation.CellEditorArgument
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType

data class TimetableState(
  val timetable: Timetable? = null,
  val cellType: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  val selectedCell: TimetableCell = TimetableCell(color = TimetableCellColor.GRAY_DARK),
  val showEditCellBottomSheet: Boolean = false,
  val showTimetableEmptyColumn: Boolean? = null,
  val showSelectCellTypeBottomSheet: Boolean = false,
)

sealed interface TimetableSideEffect {
  data object ShowNeedCreateTimetableToast : TimetableSideEffect
  data object NavigateAddTimetableCell : TimetableSideEffect
  data object NavigateCreateTimetable : TimetableSideEffect
  data object NavigateTimetableList : TimetableSideEffect
  data class NavigateCellEditor(val argument: CellEditorArgument) : TimetableSideEffect
  data class HandleException(val throwable: Throwable) : TimetableSideEffect
}
