package com.suwiki.feature.timetable.widget.timetable.cell

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.feature.timetable.widget.timetable.toGlanceText

@Composable
internal fun GlanceELearningCell(
  modifier: GlanceModifier = GlanceModifier,
  cell: TimetableCell,
) {
  val context = LocalContext.current
  val nameAndDay = "${cell.name} / ${cell.day.toGlanceText(context)}"
  val period = "(${cell.startPeriod} - ${cell.endPeriod})"

  val text = if (cell.startPeriod != 0 && cell.endPeriod != 0) {
    nameAndDay + period
  } else {
    nameAndDay
  }

  GlanceEmptyCell(
    modifier = modifier,
    text = text,
  )
}
