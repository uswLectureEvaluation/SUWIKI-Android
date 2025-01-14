package com.suwiki.presentation.timetable.timetable.component.timetable.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.suwiki.common.model.timetable.TimetableCell
import com.suwiki.common.model.timetable.TimetableCellColor
import com.suwiki.common.model.timetable.TimetableDay
import com.suwiki.presentation.common.designsystem.component.timetable.cell.EmptyCell
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.timetable.timetable.component.timetable.MINUTE10
import com.suwiki.presentation.timetable.timetable.component.timetable.MINUTE60
import com.suwiki.presentation.timetable.timetable.component.timetable.cell.ClassCell
import com.suwiki.presentation.timetable.timetable.component.timetable.cell.TimetableCellType
import com.suwiki.presentation.timetable.timetable.component.timetable.toText

internal fun TimetableCell.getStartAndEndMinute(): Pair<Int, Int> {
  val startMinute = (this.startPeriod + 8) * MINUTE60 + 3 * MINUTE10
  val endMinute = (this.endPeriod + 9) * MINUTE60 + 2 * MINUTE10
  return (startMinute to endMinute)
}

internal fun Int.endPeriodToMinute(): Int {
  return (this + 9) * MINUTE60
}

internal fun Int.isNotOnTime(): Boolean {
  return this % MINUTE60 != 0
}

@Composable
internal fun ClassColumn(
  modifier: Modifier = Modifier,
  day: TimetableDay,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  cellList: List<TimetableCell>,
  lastPeriod: Int,
  onClickClassCell: (TimetableCell) -> Unit = { _ -> },
) {
  val sortedCellList = cellList.sortedBy { it.startPeriod }
  Column(
    modifier = modifier,
  ) {
    EmptyCell(
      text = day.toText(),
    )

    var prevEndTime = 9 * MINUTE60
    sortedCellList.forEach { cell ->
      val (startMinute, endMinute) = cell.getStartAndEndMinute()
      FillEmptyTime(prevEndTime, startMinute)
      ClassCell(type = type, data = cell, onClick = onClickClassCell)
      prevEndTime = endMinute
    }

    FillEmptyTime(prevEndTime, lastPeriod.endPeriodToMinute())
  }
}

@Composable
fun FillEmptyTime(emptyStartTime: Int, emptyEndTime: Int) {
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

    EmptyCell(
      minute = insertEmptyTimeAmount,
    )

    filledEmptyTime += insertEmptyTimeAmount
  }
}

@Preview(showSystemUi = true)
@Composable
fun TimetableCellColumnPreview() {
  SuwikiTheme {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier.weight(1f),
      ) {
        EmptyCell(text = "")
        repeat(8) {
          EmptyCell(text = "${it + 9}")
        }
      }

      repeat(5) {
        ClassColumn(
          modifier = Modifier.weight(1f),
          day = TimetableDay.FRI,
          cellList = listOf(
            TimetableCell(
              name = "도전과 창조",
              professor = "김수미",
              location = "미래혁신관B202",
              startPeriod = 1,
              endPeriod = 2,
              color = TimetableCellColor.GREEN,
            ),
            TimetableCell(
              name = "도전과 창조",
              professor = "김수미",
              location = "미래혁신관B202",
              startPeriod = 3,
              endPeriod = 4,
              color = TimetableCellColor.PINK,
            ),
            TimetableCell(
              name = "도전과 창조",
              professor = "김수미",
              location = "미래혁신관B202",
              startPeriod = 6,
              endPeriod = 6,
              color = TimetableCellColor.BROWN,
            ),
          ),
          lastPeriod = 8,
        )
      }
    }
  }
}
