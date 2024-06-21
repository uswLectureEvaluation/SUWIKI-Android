package com.suwiki.presentation.timetable.widget.timetable.column

import androidx.compose.runtime.Composable
import androidx.glance.GlanceComposable
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.lazy.LazyColumn
import com.suwiki.presentation.timetable.widget.timetable.cell.GlanceEmptyCell

@Composable
@GlanceComposable
internal fun GlanceTimeColumn(
  modifier: GlanceModifier = GlanceModifier,
  maxPeriod: Int,
) {
  LazyColumn(
    modifier = modifier,
  ) {
    item {
      GlanceEmptyCell()
    }

    items(count = maxPeriod) {
      GlanceEmptyCell(text = "${it + 9}")
    }
  }
}
