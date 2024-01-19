package com.suwiki.feature.timetable.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.feature.timetable.timetable.component.timetable.MINUTE60
import com.suwiki.feature.timetable.timetable.component.timetable.Timetable
import com.suwiki.feature.timetable.timetable.component.timetable.column.endPeriodToMinute
import com.suwiki.feature.timetable.timetable.component.timetable.column.getStartAndEndMinute
import com.suwiki.feature.timetable.timetable.component.timetable.toText
import com.suwiki.feature.timetable.widget.timetable.GlanceTimetable
import com.suwiki.feature.timetable.widget.timetable.cell.GlanceClassCell
import com.suwiki.feature.timetable.widget.timetable.cell.GlanceEmptyCell
import com.suwiki.feature.timetable.widget.timetable.column.GlanceClassColumn
import com.suwiki.feature.timetable.widget.timetable.column.GlanceFillEmptyTime
import com.suwiki.feature.timetable.widget.timetable.column.GlanceTimeColumn

object TimetableWidget : GlanceAppWidget() {

  override val sizeMode = SizeMode.Exact

  override suspend fun provideGlance(context: Context, id: GlanceId) {
    provideContent {

      val size = LocalSize.current

      GlanceTimetable(
        modifier = GlanceModifier
          .fillMaxSize(),
        size = size.width,
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
              endPeriod = 9,
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
              endPeriod = 10,
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
}

