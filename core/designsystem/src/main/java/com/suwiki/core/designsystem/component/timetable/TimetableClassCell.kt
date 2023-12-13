package com.suwiki.core.designsystem.component.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.ui.extension.suwikiClickable

enum class TimetableCellType {
  CLASSNAME,
  CLASSNAME_LOCATION,
  CLASSNAME_PROFESSOR,
  CLASSNAME_PROFESSOR_LOCATION,
}

internal fun timetableCellColorToHex(color: TimetableCellColor): Long {
  return when (color) {
    TimetableCellColor.BROWN -> 0xFFC28F62
    TimetableCellColor.LIGHT_BROWN -> 0xFFD4AC89
    TimetableCellColor.ORANGE -> 0xFFFFC152
    TimetableCellColor.APRICOT -> 0xFFFFC152
    TimetableCellColor.PURPLE -> 0xFF7E3348
    TimetableCellColor.PURPLE_LIGHT -> 0xFFC97189
    TimetableCellColor.RED_LIGHT -> 0xFFFE8888
    TimetableCellColor.PINK -> 0xFFFDA1E4
    TimetableCellColor.BROWN_DARK -> 0xFF614730
    TimetableCellColor.GREEN_DARK -> 0xFF96B277
    TimetableCellColor.GREEN -> 0xFFA5DC81
    TimetableCellColor.GREEN_LIGHT -> 0xFF99ECA5
    TimetableCellColor.NAVY_DARK -> 0xFF435796
    TimetableCellColor.NAVY -> 0xFF5772C1
    TimetableCellColor.NAVY_LIGHT -> 0xFF899DFE
    TimetableCellColor.VIOLET -> 0xFFCC9AF3
    TimetableCellColor.GRAY_DARK -> 0xFF525252
    TimetableCellColor.GRAY -> 0xFFC2C1BD
    TimetableCellColor.SKY -> 0xFF89C8FE
    TimetableCellColor.VIOLET_LIGHT -> 0xFFC0C6E0
  }
}

@Composable
fun TimetableClassCell(
  modifier: Modifier = Modifier,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  data: TimetableCell,
  onClick: (TimetableCell) -> Unit = { _ -> },
) {
  val (showProfessor, showLocation) = when (type) {
    TimetableCellType.CLASSNAME -> (false to false)
    TimetableCellType.CLASSNAME_LOCATION -> (false to true)
    TimetableCellType.CLASSNAME_PROFESSOR -> (true to false)
    TimetableCellType.CLASSNAME_PROFESSOR_LOCATION -> (true to true)
  }

  val height = (data.endPeriod - data.startPeriod + 1) * timetableHeightPerHour - 8.dp

  Column(
    modifier = modifier
      .fillMaxWidth()
      .height(height)
      .border(width = timetableBorderWidth, color = Color.Gray)
      .padding(timetableBorderWidth)
      .background(Color(timetableCellColorToHex(data.color)))
      .suwikiClickable {
        onClick(data)
      },
  ) {
    Text(text = data.name)

    if (showProfessor) Text(text = data.professor)

    if (showLocation) Text(text = data.location)
  }
}

@Preview
@Composable
fun TimetableClassCellPreview() {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
  ) {
    TimetableClassCell(
      modifier = Modifier.width(50.dp),
      type = TimetableCellType.CLASSNAME,
      data = TimetableCell(
        name = "도전과 창조",
        professor = "김수미",
        location = "미래혁신관B202",
        startPeriod = 1,
        endPeriod = 3,
        color = TimetableCellColor.GREEN,
      ),
    )

    TimetableClassCell(
      modifier = Modifier.width(50.dp),
      type = TimetableCellType.CLASSNAME_PROFESSOR,
      data = TimetableCell(
        name = "도전과 창조",
        professor = "김수미",
        location = "미래혁신관B202",
        startPeriod = 1,
        endPeriod = 3,
        color = TimetableCellColor.GREEN,
      ),
    )

    TimetableClassCell(
      modifier = Modifier.width(50.dp),
      type = TimetableCellType.CLASSNAME_LOCATION,
      data = TimetableCell(
        name = "도전과 창조",
        professor = "김수미",
        location = "미래혁신관B202",
        startPeriod = 1,
        endPeriod = 3,
        color = TimetableCellColor.GREEN,
      ),
    )

    TimetableClassCell(
      modifier = Modifier.width(50.dp),
      type = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
      data = TimetableCell(
        name = "도전과 창조",
        professor = "김수미",
        location = "미래혁신관B202",
        startPeriod = 1,
        endPeriod = 3,
        color = TimetableCellColor.GREEN,
      ),
    )
  }
}