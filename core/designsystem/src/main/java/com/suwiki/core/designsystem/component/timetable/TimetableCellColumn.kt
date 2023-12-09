package com.suwiki.core.designsystem.component.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.suwiki.core.designsystem.R
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlin.math.max

private const val MIN_END_TIME = 8

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

internal fun Int.toMinute(): Int {
  return (this + 8) * HOUR_TO_MIN + HALF_HOUR_TO_MIN
}

internal fun Int.isNotOnTime(): Boolean {
  return this % HOUR_TO_MIN == HALF_HOUR_TO_MIN
}

@Composable
fun TimetableCellColumn(
  modifier: Modifier = Modifier,
  day: TimetableDay,
  cellList: List<TimetableCell>,
  endPeriod: Int,
) {
  val sortedCellList = cellList.sortedBy { it.startPeriod }
  Column(
    modifier = modifier,
  ) {
    TimetableEmptyCell(
      text = timetableDayToString(day),
    )

    var emptyStartTime = 9 * HOUR_TO_MIN
    sortedCellList.forEach { cell ->
      val emptyEndTime = cell.startPeriod.toMinute()

      while (emptyStartTime < emptyEndTime) {
        val insertEmptyTimeAmount = when {
          // 정각이 아니거나 종료 시간까지 30분이 남은 경우, 30분을 채운다.
          emptyStartTime.isNotOnTime() || emptyEndTime - emptyStartTime == HALF_HOUR_TO_MIN -> {
            HALF_HOUR_TO_MIN
          }
          // 그렇지 않다면 1시간을 채운다.
          else -> {
            HOUR_TO_MIN
          }
        }
        TimetableEmptyCell(
          minute = insertEmptyTimeAmount
        )
        emptyStartTime += insertEmptyTimeAmount
      }

      TimetableClassCell(data = cell)

      emptyStartTime = cell.endPeriod.toMinute()
    }

    if (emptyStartTime.isNotOnTime()) {
      TimetableEmptyCell(
        minute = HALF_HOUR_TO_MIN
      )

      emptyStartTime += HALF_HOUR_TO_MIN
    }

    while (emptyStartTime < max(endPeriod, MIN_END_TIME).toMinute()) {
      TimetableEmptyCell(
        minute = HOUR_TO_MIN
      )

      emptyStartTime += HOUR_TO_MIN
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun TimetableCellColumnPreview() {
  Row(
    modifier = Modifier.fillMaxWidth(),
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
            endPeriod = 3,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            startPeriod = 4,
            endPeriod = 5,
            color = TimetableCellColor.PINK,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            startPeriod = 5,
            endPeriod = 6,
            color = TimetableCellColor.BROWN,
          ),
        ),
        endPeriod = 3,
      )
    }
  }
}
