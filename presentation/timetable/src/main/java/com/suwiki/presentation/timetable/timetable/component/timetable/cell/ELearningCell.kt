package com.suwiki.presentation.common.designsystem.component.timetable.cell

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.suwiki.common.model.timetable.TimetableCell
import com.suwiki.presentation.common.ui.extension.suwikiClickable
import com.suwiki.presentation.timetable.timetable.component.timetable.toText

@Composable
internal fun ELearningCell(
  modifier: Modifier = Modifier,
  cell: TimetableCell,
  onClickClassCell: (TimetableCell) -> Unit = { _ -> },
) {
  val nameAndDay = "${cell.name} / ${cell.day.toText()}"
  val period = "(${cell.startPeriod} - ${cell.endPeriod})"

  val text = if (cell.startPeriod != 0 && cell.endPeriod != 0) {
    nameAndDay + period
  } else {
    nameAndDay
  }

  EmptyCell(
    modifier = modifier
      .fillMaxWidth()
      .suwikiClickable {
        onClickClassCell(cell)
      },
    text = text,
  )
}
