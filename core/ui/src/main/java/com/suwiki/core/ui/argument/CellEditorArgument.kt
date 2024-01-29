package com.suwiki.core.ui.argument

import android.os.Parcelable
import com.suwiki.core.model.timetable.Cell
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class CellEditorArgument(
  val oldCellId: String = "",
  val name: String = "",
  val professorName: String = "",
  val cellList: List<CellArgument> = emptyList(),
  val timetableCellColor: TimetableCellColor = TimetableCellColor.entries.shuffled().first(),
  val isEditMode: Boolean = oldCellId.isNotEmpty()
) : Parcelable

@Parcelize
data class CellArgument(
  val location: String = "",
  val day: TimetableDay = TimetableDay.MON,
  val startPeriod: String = "",
  val endPeriod: String = "",
) : Parcelable

fun OpenLecture.toCellEditorArgument() = CellEditorArgument(
  name = name,
  professorName = professorName,
  cellList = originalCellList.map { it.toCellArgument() },
)

fun Cell.toCellArgument() = CellArgument(
  location = location,
  day = day,
  startPeriod = startPeriod.toString(),
  endPeriod = endPeriod.toString(),
)

fun TimetableCell.toCellEditorArgument() = CellEditorArgument(
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
