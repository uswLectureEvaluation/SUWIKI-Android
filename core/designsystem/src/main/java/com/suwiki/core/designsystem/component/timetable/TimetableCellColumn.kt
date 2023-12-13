package com.suwiki.core.designsystem.component.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay

@Composable
internal fun timetableDayToString(day: TimetableDay): String {
  return when (day) {
    TimetableDay.MON -> stringResource(R.string.word_mon)
    TimetableDay.TUE -> stringResource(R.string.word_tue)
    TimetableDay.WED -> stringResource(R.string.word_wed)
    TimetableDay.THU -> stringResource(R.string.word_thu)
    TimetableDay.FRI -> stringResource(R.string.word_fri)
    TimetableDay.SAT -> stringResource(R.string.word_sat)
    TimetableDay.E_LEARNING -> stringResource(R.string.word_elearning)
  }
}

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
fun TimetableCellColumn(
  modifier: Modifier = Modifier,
  day: TimetableDay,
  cellList: List<TimetableCell>,
  lastPeriod: Int,
) {
  val sortedCellList = cellList.sortedBy { it.startPeriod }
  Column(
    modifier = modifier,
  ) {
    TimetableEmptyCell(
      text = timetableDayToString(day),
    )

    var prevEndTime = 9 * MINUTE60
    sortedCellList.forEach { cell ->
      val (startMinute, endMinute) = cell.getStartAndEndMinute()
      FillEmptyTime(prevEndTime, startMinute)
      TimetableClassCell(data = cell)
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

    TimetableEmptyCell(
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
        TimetableEmptyCell(text = "")
        repeat(8) {
          TimetableEmptyCell(text = "${it + 9}")
        }
      }

      repeat(5) {
        TimetableCellColumn(
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
