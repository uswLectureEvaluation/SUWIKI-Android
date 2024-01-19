package com.suwiki.feature.timetable.widget.timetable.column

import androidx.compose.runtime.Composable
import androidx.glance.GlanceComposable
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.LazyListScope
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.feature.timetable.timetable.component.timetable.MINUTE60
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType
import com.suwiki.feature.timetable.timetable.component.timetable.column.endPeriodToMinute
import com.suwiki.feature.timetable.timetable.component.timetable.column.getStartAndEndMinute
import com.suwiki.feature.timetable.timetable.component.timetable.column.isNotOnTime
import com.suwiki.feature.timetable.widget.timetable.cell.GlanceClassCell
import com.suwiki.feature.timetable.widget.timetable.cell.GlanceEmptyCell
import com.suwiki.feature.timetable.widget.timetable.toGlanceText

@Composable
@GlanceComposable
internal fun GlanceClassColumn(
  modifier: GlanceModifier = GlanceModifier,
  day: TimetableDay,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  cellList: List<TimetableCell>,
  lastPeriod: Int,
) {
  val context = LocalContext.current
  val sortedCellList = cellList.sortedBy { it.startPeriod }
  LazyColumn(
    modifier = modifier,
  ) {
    item {
      GlanceEmptyCell(
        text = day.toGlanceText(context),
      )
    }

    var prevEndTime = 9 * MINUTE60
    sortedCellList.forEach { cell ->
      val (startMinute, endMinute) = cell.getStartAndEndMinute()
      this@LazyColumn.glanceFillEmptyTime(prevEndTime, startMinute)
      item {
        GlanceClassCell(type = type, data = cell)
      }
      prevEndTime = endMinute
    }

    this.glanceFillEmptyTime(prevEndTime, lastPeriod.endPeriodToMinute())
  }
}

fun LazyListScope.glanceFillEmptyTime(emptyStartTime: Int, emptyEndTime: Int) {
  var filledEmptyTime = emptyStartTime

  while (filledEmptyTime < emptyEndTime) {
    val insertEmptyTimeAmount = when {
      // 종료 시각까지 1시간이 안되는 경우, 종료 시각까지 남은 시간을 채운다.
      emptyEndTime - filledEmptyTime < MINUTE60 -> {
        emptyEndTime - filledEmptyTime
      }

      // 정각이 아닌 경우 정각까지 남은 시간을 채운다.
      filledEmptyTime.isNotOnTime() -> {
        MINUTE60 - filledEmptyTime % MINUTE60
      }

      // 그렇지 않다면 1시간을 채운다.
      else -> {
        MINUTE60
      }
    }

    item {
      GlanceEmptyCell(
        minute = insertEmptyTimeAmount,
      )
    }

    filledEmptyTime += insertEmptyTimeAmount
  }
}
