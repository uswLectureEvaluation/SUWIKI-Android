package com.suwiki.core.designsystem.component.timetable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.timetable.cell.ELearningCell
import com.suwiki.core.designsystem.component.timetable.column.TimeColumn
import com.suwiki.core.designsystem.component.timetable.column.ClassColumn
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.suwikiClickable
import kotlin.math.max

internal const val MIN_MAX_PERIOD = 8

internal fun List<TimetableCell>.maxPeriod(): Int {
  return max((maxOfOrNull { it.endPeriod }?.plus(1)) ?: MIN_MAX_PERIOD, MIN_MAX_PERIOD)
}

@Composable
fun Timetable(
  modifier: Modifier = Modifier,
  timetable: Timetable,
  onClickAdd: () -> Unit = {},
  onClickHamburger: () -> Unit = {},
  onClickSetting: () -> Unit = {},
  onClickClassCell: (TimetableCell) -> Unit = { _ -> },
) {
  val scrollState = rememberScrollState()
  val maxPeriod by remember {
    derivedStateOf { timetable.cellList.maxPeriod() }
  }

  // TODO 리컴포지션 최적화 필요
  val cellGroupedByDay = timetable.cellList.groupBy { it.day }

  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Header(
      name = timetable.name,
      onClickAdd = onClickAdd,
      onClickHamburger = onClickHamburger,
      onClickSetting = onClickSetting,
    )

    Body(
      scrollState = scrollState,
      maxPeriod = maxPeriod,
      cellGroupedByDay = cellGroupedByDay,
      onClickClassCell = onClickClassCell,
    )
  }
}

@Composable
private fun Header(
  modifier: Modifier = Modifier,
  name: String,
  onClickAdd: () -> Unit = {},
  onClickHamburger: () -> Unit = {},
  onClickSetting: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = 24.dp, end = 20.dp),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.spacedBy(18.dp),
  ) {
    Text(
      modifier = Modifier.weight(1f),
      overflow = TextOverflow.Ellipsis,
      text = name,
      style = SuwikiTheme.typography.header1,
      color = Black,
      maxLines = 1,
    )

    Icon(
      modifier = Modifier.suwikiClickable(onClick = onClickHamburger),
      painter = painterResource(id = R.drawable.ic_timetable_add),
      contentDescription = "",
      tint = Gray95,
    )

    Icon(
      modifier = Modifier.suwikiClickable(onClick = onClickAdd),
      painter = painterResource(id = R.drawable.ic_timetable_hamburger),
      contentDescription = "",
      tint = Gray95,
    )

    Icon(
      modifier = Modifier.suwikiClickable(onClick = onClickSetting),

      painter = painterResource(id = R.drawable.ic_timetable_setting),
      contentDescription = "",
      tint = Gray95,
    )
  }
}

@Composable
private fun Body(
  modifier: Modifier = Modifier,
  scrollState: ScrollState,
  maxPeriod: Int,
  cellGroupedByDay: Map<TimetableDay, List<TimetableCell>>,
  onClickClassCell: (TimetableCell) -> Unit,
) {
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
            day = day,
            cellList = cellGroupedByDay[day] ?: emptyList(),
            lastPeriod = maxPeriod,
            onClickClassCell = onClickClassCell,
          )
        }
    }

    cellGroupedByDay
      .filter { it.key in listOf(TimetableDay.SAT, TimetableDay.E_LEARNING) }
      .flatMap { it.value }
      .forEach { cell ->
        ELearningCell(
          onClickClassCell = onClickClassCell,
          cell = cell,
        )
      }
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
        name = "Previewaaaaaaaaaeeeeeaaaa",
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
