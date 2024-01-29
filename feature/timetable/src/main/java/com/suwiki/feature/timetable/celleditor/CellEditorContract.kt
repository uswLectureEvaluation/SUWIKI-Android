package com.suwiki.feature.timetable.celleditor

import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.argument.CellArgument
import com.suwiki.core.ui.argument.CellEditorArgument
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class CellEditorState(
  val lectureName: String = "",
  val professorName: String = "",
  val cellStateList: PersistentList<CellState> = persistentListOf(CellState()),
  val selectedTimetableCellColor: TimetableCellColor = TimetableCellColor.GRAY_DARK,
)

data class CellState(
  val location: String = "",
  val day: TimetableDay = TimetableDay.MON,
  val startPeriod: String = "",
  val endPeriod: String = "",
)

internal fun CellArgument.toState() = CellState(
  location = location,
  day = day,
  startPeriod = startPeriod,
  endPeriod = endPeriod,
)

internal fun CellEditorArgument.toState() = CellEditorState(
  lectureName = name,
  professorName = professorName,
  cellStateList = cellList.map { it.toState() }.toPersistentList(),
  selectedTimetableCellColor = timetableCellColor,
)

sealed interface CellEditorSideEffect {
  data object PopBackStack : CellEditorSideEffect
  data class HandleException(val throwable: Throwable) : CellEditorSideEffect
  data class ShowToast(val msg: String) : CellEditorSideEffect
  data object ShowAddSuccessCellToast : CellEditorSideEffect
  data object ShowEditSuccessCellToast : CellEditorSideEffect
  data object ShowNeedLectureNameToast : CellEditorSideEffect
  data object ShowNeedProfessorNameToast : CellEditorSideEffect
  data object ShowNeedLocationToast : CellEditorSideEffect
}
