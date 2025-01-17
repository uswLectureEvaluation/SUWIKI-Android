package com.suwiki.presentation.timetable.timetable.component.timetable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.common.model.timetable.Timetable
import com.suwiki.common.model.timetable.TimetableCell
import com.suwiki.common.model.timetable.TimetableCellColor
import com.suwiki.common.model.timetable.TimetableDay
import com.suwiki.presentation.common.designsystem.component.timetable.cell.ELearningCell
import com.suwiki.presentation.common.designsystem.component.timetable.column.TimeColumn
import com.suwiki.presentation.common.designsystem.theme.GrayF6
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.timetable.timetable.component.timetable.cell.TimetableCellType
import com.suwiki.presentation.timetable.timetable.component.timetable.column.ClassColumn
import kotlin.math.max

private const val MIN_MAX_PERIOD = 8

internal fun List<TimetableCell>.maxPeriod(): Int {
  return max((maxOfOrNull { it.endPeriod }?.plus(1)) ?: MIN_MAX_PERIOD, MIN_MAX_PERIOD)
}

@Composable
fun Timetable(
  modifier: Modifier = Modifier,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  timetable: Timetable,
  onClickTimetableCell: (TimetableCell) -> Unit = { _ -> },
) {
  val scrollState = rememberScrollState()

  val maxPeriod = timetable.cellList.maxPeriod()

  // TODO 리컴포지션 최적화 필요
  val cellGroupedByDay = timetable.cellList.groupBy { it.day }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .border(width = timetableBorderWidth, color = GrayF6)
      .verticalScroll(scrollState),
  ) {
    Row {
      TimeColumn(
        modifier = Modifier.weight(1f),
        maxPeriod = maxPeriod,
      )

      TimetableDay.entries
        .sortedBy { it.idx }
        .filter { it !in listOf(TimetableDay.SAT, TimetableDay.E_LEARNING) }
        .forEach { day ->
          ClassColumn(
            modifier = Modifier.weight(1f),
            type = type,
            day = day,
            cellList = cellGroupedByDay[day] ?: emptyList(),
            lastPeriod = maxPeriod,
            onClickClassCell = onClickTimetableCell,
          )
        }
    }

    cellGroupedByDay
      .filter { it.key in listOf(TimetableDay.SAT, TimetableDay.E_LEARNING) }
      .flatMap { it.value }
      .forEach { cell ->
        ELearningCell(
          onClickClassCell = onClickTimetableCell,
          cell = cell,
        )
      }

    Spacer(modifier = Modifier.size(100.dp))
  }
}

@Preview(showSystemUi = true)
@Composable
fun TimetablePreview() {
  SuwikiTheme {
    Timetable(
      timetable = Timetable(
        createTime = 0L,
        year = "",
        semester = "",
        name = "프리뷰입니다 프리뷰입니다 프리뷰입니다",
        cellList = listOf(
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.E_LEARNING,
            startPeriod = 7,
            endPeriod = 8,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.SAT,
            startPeriod = 7,
            endPeriod = 8,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.E_LEARNING,
            startPeriod = 0,
            endPeriod = 0,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.MON,
            startPeriod = 7,
            endPeriod = 8,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.FRI,
            startPeriod = 1,
            endPeriod = 2,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.FRI,
            startPeriod = 3,
            endPeriod = 4,
            color = TimetableCellColor.PINK,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.FRI,
            startPeriod = 6,
            endPeriod = 6,
            color = TimetableCellColor.BROWN,
          ),
        ),
      ),
    )
  }
}
