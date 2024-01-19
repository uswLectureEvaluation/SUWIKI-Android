package com.suwiki.feature.timetable.widget.timetable.cell

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.glance.GlanceComposable
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.suwiki.core.designsystem.theme.GrayCB
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType
import com.suwiki.feature.timetable.timetable.component.timetable.cell.toHex
import com.suwiki.feature.timetable.timetable.component.timetable.timetableBorderWidth
import com.suwiki.feature.timetable.timetable.component.timetable.timetableHeightPerHour
import com.suwiki.feature.timetable.widget.timetable.glanceTimetableBorderWidth
import com.suwiki.feature.timetable.widget.timetable.glanceTimetableHeightPerHour

@Composable
@GlanceComposable
internal fun GlanceClassCell(
  modifier: GlanceModifier = GlanceModifier,
  type: TimetableCellType = TimetableCellType.CLASSNAME_PROFESSOR_LOCATION,
  data: TimetableCell,
) {
  val (showProfessor, showLocation) = when (type) {
    TimetableCellType.CLASSNAME -> (false to false)
    TimetableCellType.CLASSNAME_LOCATION -> (false to true)
    TimetableCellType.CLASSNAME_PROFESSOR -> (true to false)
    TimetableCellType.CLASSNAME_PROFESSOR_LOCATION -> (true to true)
  }

  val height = (data.endPeriod - data.startPeriod + 1) * glanceTimetableHeightPerHour - 8.dp


  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(height)
      .background(GrayF6)
      .padding(glanceTimetableBorderWidth)
  ) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .background(Color(data.color.toHex())),
    ) {
      Text(
        style = TextStyle(
          color = ColorProvider(White),
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
        ),
        text = data.name,
      )

      if (showProfessor) {
        Text(
          style = TextStyle(
            color = ColorProvider(White),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
          ),
          text = data.professor,
        )
      }

      if (showLocation) {
        Text(
          style = TextStyle(
            color = ColorProvider(White),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
          ),
          text = data.location,
        )
      }
    }
  }

}
