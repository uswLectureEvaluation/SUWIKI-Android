package com.suwiki.feature.timetable.timetable.component.timetable.cell

import androidx.annotation.StringRes
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
import com.suwiki.feature.common.designsystem.theme.GrayF6
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.extension.suwikiClickable
import com.suwiki.feature.common.ui.util.timetableCellColorHexMap
import com.suwiki.feature.timetable.R
import com.suwiki.feature.timetable.timetable.component.timetable.timetableBorderWidth
import com.suwiki.feature.timetable.timetable.component.timetable.timetableHeightPerHour

enum class TimetableCellType(@StringRes val stringResId: Int) {
  CLASSNAME(R.string.timetable_cell_type_classname),
  CLASSNAME_LOCATION(R.string.timetable_cell_type_classname_location),
  CLASSNAME_PROFESSOR(R.string.timetable_cell_type_classname_professor),
  CLASSNAME_PROFESSOR_LOCATION(R.string.timetable_cell_type_classname_professor_location),
  ;

  companion object {
    fun getType(value: String?) = TimetableCellType
      .entries
      .find { it.name == value }
      ?: CLASSNAME_PROFESSOR_LOCATION
  }
}

fun TimetableCellColor.toHex(): Long = timetableCellColorHexMap[this]!!

@Composable
internal fun ClassCell(
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
      .border(width = timetableBorderWidth, color = GrayF6)
      .padding(timetableBorderWidth)
      .background(Color(data.color.toHex()))
      .suwikiClickable {
        onClick(data)
      },
  ) {
    Text(
      style = SuwikiTheme.typography.caption3,
      text = data.name,
      color = White,
    )

    if (showProfessor) {
      Text(
        style = SuwikiTheme.typography.caption6,
        text = data.professor,
        color = White,
      )
    }

    if (showLocation) {
      Text(
        style = SuwikiTheme.typography.caption6,
        text = data.location,
        color = White,
      )
    }
  }
}

@Preview
@Composable
fun TimetableClassCellPreview() {
  SuwikiTheme {
    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      ClassCell(
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

      ClassCell(
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

      ClassCell(
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

      ClassCell(
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
}
