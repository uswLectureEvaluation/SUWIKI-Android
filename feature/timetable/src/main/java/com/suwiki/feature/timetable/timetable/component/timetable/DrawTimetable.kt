package com.suwiki.feature.timetable.timetable.component.timetable

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.suwiki.core.designsystem.component.timetable.cell.ELearningCell
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.feature.timetable.openlecture.toText
import com.suwiki.feature.timetable.timetable.component.timetable.cell.ClassCell
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType

@Composable
fun DrawTimetable(
  modifier: Modifier = Modifier,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  timetable: Timetable,
  onClickTimetableCell: (TimetableCell) -> Unit = { _ -> },
) {
  val maxPeriod = timetable.cellList.maxPeriod()
  val textMeasurer = rememberTextMeasurer()
  val height = (maxPeriod + 1) * timetableHeightPerHour

  val context = LocalContext.current
  val density = LocalDensity.current
  val dayTextStyle = SuwikiTheme.typography.caption4.copy(color = Gray6A)
  val timeTextStyle = SuwikiTheme.typography.caption4.copy(color = Gray6A)

  var unitWidth: Float by remember {
    mutableStateOf(0f)
  }

  Column(
    modifier = Modifier.verticalScroll(rememberScrollState())
  ) {
    Box(
      modifier = modifier
        .height(height)
        .fillMaxWidth()
        .background(White)
        .drawBehind {
          unitWidth = size.width / 6
          val unitHeight = timetableHeightPerHour.toPx()
          val unitSize = Size(width = unitWidth, height = unitHeight)
          val strokeColor = GrayF6
          val strokeWidth = 1.dp.toPx()

          drawHorizontalGrid(
            maxPeriod = maxPeriod,
            timeTextStyle = timeTextStyle,
            textMeasurer = textMeasurer,
            unitWidth = unitWidth,
            unitHeight = unitHeight,
            unitSize = unitSize,
            strokeColor = strokeColor,
            strokeWidth = strokeWidth,
          )

          drawVerticalGrid(
            context = context,
            dayTextStyle = dayTextStyle,
            textMeasurer = textMeasurer,
            unitWidth = unitWidth,
            unitHeight = unitHeight,
            unitSize = unitSize,
            strokeColor = strokeColor,
            strokeWidth = strokeWidth,
          )
        },
    ) {
      val cellWidth = with(density) { unitWidth.toDp() }
      timetable.cellList.filter { it.day !in listOf(TimetableDay.E_LEARNING, TimetableDay.SAT) }.forEach { cell ->
        DrawClassCell(
          width = cellWidth,
          type = type,
          data = cell,
          onClick = onClickTimetableCell,
        )
      }
    }

    timetable.cellList
      .filter { it.day in listOf(TimetableDay.SAT, TimetableDay.E_LEARNING) }
      .forEach { cell ->
        ELearningCell(
          onClickClassCell = onClickTimetableCell,
          cell = cell,
        )
      }

    Spacer(modifier = Modifier.size(100.dp))
  }
}

private fun DrawScope.drawHorizontalGrid(
  maxPeriod: Int,
  timeTextStyle: TextStyle = TextStyle.Default,
  textMeasurer: TextMeasurer,
  unitWidth: Float,
  unitHeight: Float,
  unitSize: Size,
  strokeColor: Color,
  strokeWidth: Float,
) {
  var startHeight = 0f

  repeat(maxPeriod) {
    drawLine(
      start = Offset(x = 0f, y = startHeight),
      end = Offset(x = size.width, y = startHeight),
      color = strokeColor,
      strokeWidth = strokeWidth,
    )
    drawLine(
      start = Offset(x = 0f, y = startHeight + unitHeight),
      end = Offset(x = size.width, y = startHeight + unitHeight),
      color = strokeColor,
      strokeWidth = strokeWidth,
    )

    val textLayoutResult: TextLayoutResult =
      textMeasurer.measure(
        text = "${it + 9}",
        style = timeTextStyle,
      )
    val textSize = textLayoutResult.size

    drawText(
      textMeasurer = textMeasurer,
      text = "${it + 9}",
      style = timeTextStyle,
      topLeft = Offset(
        x = unitWidth / 2 - textSize.width / 2,
        y = startHeight + unitHeight + unitHeight / 2 - textSize.height / 2,
      ),
      size = unitSize,
    )

    startHeight += unitHeight
  }
}


private fun DrawScope.drawVerticalGrid(
  context: Context,
  dayTextStyle: TextStyle = TextStyle.Default,
  textMeasurer: TextMeasurer,
  unitWidth: Float,
  unitHeight: Float,
  unitSize: Size,
  strokeColor: Color,
  strokeWidth: Float,
) {
  var startWidth = 0f
  drawLine(
    start = Offset(x = startWidth, y = 0f),
    end = Offset(x = startWidth, y = size.height),
    color = strokeColor,
    strokeWidth = strokeWidth,
  )

  startWidth += unitWidth

  TimetableDay.entries.filter { it != TimetableDay.E_LEARNING }.forEach { day ->
    drawLine(
      start = Offset(x = startWidth, y = 0f),
      end = Offset(x = startWidth, y = size.height),
      color = strokeColor,
      strokeWidth = strokeWidth,
    )

    val textLayoutResult: TextLayoutResult =
      textMeasurer.measure(
        text = day.toText(context),
        style = dayTextStyle,
      )
    val textSize = textLayoutResult.size

    drawText(
      textMeasurer = textMeasurer,
      text = day.toText(context),
      style = dayTextStyle,
      topLeft = Offset(
        x = startWidth + unitWidth / 2 - textSize.width / 2,
        y = unitHeight / 2 - textSize.height / 2,
      ),
      size = unitSize,
    )

    startWidth += unitWidth
  }

}

@Preview(showSystemUi = true)
@Composable
fun DrawTimetablePreview() {
  SuwikiTheme {
    DrawTimetable(
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
            day = TimetableDay.SAT,
            startPeriod = 7,
            endPeriod = 8,
            color = TimetableCellColor.GREEN,
          ),
          TimetableCell(
            name = "도전과 창조",
            professor = "김수미",
            location = "미래혁신관B202",
            day = TimetableDay.MON,
            startPeriod = 1,
            endPeriod = 2,
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

