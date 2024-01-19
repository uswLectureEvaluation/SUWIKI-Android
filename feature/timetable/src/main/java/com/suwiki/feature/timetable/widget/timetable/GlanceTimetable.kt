package com.suwiki.feature.timetable.widget.timetable

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.width
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType
import com.suwiki.feature.timetable.timetable.component.timetable.maxPeriod
import com.suwiki.feature.timetable.widget.timetable.cell.GlanceELearningCell
import com.suwiki.feature.timetable.widget.timetable.column.GlanceClassColumn
import com.suwiki.feature.timetable.widget.timetable.column.GlanceTimeColumn
import kotlin.math.max

@Composable
@GlanceComposable
fun GlanceTimetable(
  modifier: GlanceModifier = GlanceModifier,
  size: Dp,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  timetable: Timetable,
) {
  val maxPeriod = timetable.cellList.maxPeriod()

  val cellGroupedByDay = timetable.cellList.groupBy { it.day }

  Column(
    modifier = modifier
      .fillMaxSize(),
  ) {
    Row {
      GlanceTimeColumn(
        modifier = GlanceModifier.width(size / 6),
        maxPeriod = maxPeriod,
      )

      TimetableDay.entries
        .sortedBy { it.idx }
        .filter { it !in listOf(TimetableDay.SAT, TimetableDay.E_LEARNING) }
        .forEach { day ->
          GlanceClassColumn(
            modifier = GlanceModifier.width(size / 6),
            type = type,
            day = day,
            cellList = cellGroupedByDay[day] ?: emptyList(),
            lastPeriod = maxPeriod,
          )
        }
    }

    cellGroupedByDay
      .filter { it.key in listOf(TimetableDay.SAT, TimetableDay.E_LEARNING) }
      .flatMap { it.value }
      .forEach { cell ->
        GlanceELearningCell(
          modifier = GlanceModifier.fillMaxWidth(),
          cell = cell,
        )
      }
  }
}
