package com.suwiki.feature.timetable.celleditor

import com.suwiki.core.model.timetable.Cell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CellEditorState(
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

sealed interface CellEditorSideEffect {
  data object PopBackStack : CellEditorSideEffect
  data class HandleException(val throwable: Throwable) : CellEditorSideEffect
  data class ShowOverlapCellToastEditor(val msg: String) : CellEditorSideEffect
  data object ShowSuccessCellToastEditor : CellEditorSideEffect
  data object ShowNeedLectureNameToast : CellEditorSideEffect
  data object ShowNeedProfessorNameToast : CellEditorSideEffect
  data object ShowNeedLocationToast : CellEditorSideEffect
}
