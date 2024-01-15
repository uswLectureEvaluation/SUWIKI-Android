package com.suwiki.feature.timetable.addcell

import com.suwiki.core.model.timetable.Cell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class AddCellState(
  val lectureName: String = "",
  val professorName: String = "",
  val cellStateList: PersistentList<CellState> = persistentListOf(CellState()),
  val selectedTimetableCellColor: TimetableCellColor = TimetableCellColor.entries.shuffled().first(),
)

data class CellState(
  val location: String = "",
  val day: TimetableDay = TimetableDay.MON,
  val startPeriod: String = "",
  val endPeriod: String = "",
)

internal fun Cell.toState() = CellState(
  location = location,
  day = day,
  startPeriod = startPeriod.toString(),
  endPeriod = endPeriod.toString(),
)

sealed interface AddCellSideEffect {
  data object PopBackStack : AddCellSideEffect
  data class HandleException(val throwable: Throwable) : AddCellSideEffect
  data class ShowOverlapCellToast(val msg: String) : AddCellSideEffect
  data object ShowSuccessAddCellToast : AddCellSideEffect
  data object ShowNeedLectureNameToast : AddCellSideEffect
  data object ShowNeedProfessorNameToast : AddCellSideEffect
  data object ShowNeedLocationToast : AddCellSideEffect
}