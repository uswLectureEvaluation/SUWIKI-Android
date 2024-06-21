package com.suwiki.presentation.timetable.navigation.argument

import com.suwiki.core.model.timetable.Cell
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlinx.serialization.Serializable

@Serializable
data class CellEditorArgument(
  val oldCellId: String = "",
  val name: String = "",
  val professorName: String = "",
  val cellList: List<CellArgument> = emptyList(),
  val timetableCellColor: TimetableCellColor = TimetableCellColor.entries.shuffled().first(),
) {
  val isEditMode = oldCellId.isNotEmpty()
}

@Serializable
data class CellArgument(
  val location: String = "",
  val day: TimetableDay = TimetableDay.MON,
  val startPeriod: String = "",
  val endPeriod: String = "",
)

internal fun OpenLecture.toCellEditorArgument() = CellEditorArgument(
  name = name,
  professorName = professorName,
  cellList = originalCellList.map { it.toCellArgument() },
)

internal fun Cell.toCellArgument() = CellArgument(
  location = location,
  day = day,
  startPeriod = startPeriod.toString(),
  endPeriod = endPeriod.toString(),
)

internal fun TimetableCell.toCellEditorArgument() = CellEditorArgument(
  oldCellId = id,
  name = name,
  professorName = professor,
  cellList = listOf(
    CellArgument(
      location = location,
      day = day,
      startPeriod = startPeriod.toString(),
      endPeriod = endPeriod.toString(),
    ),
  ),
  timetableCellColor = color,
)
