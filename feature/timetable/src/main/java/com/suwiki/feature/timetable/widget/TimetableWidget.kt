package com.suwiki.feature.timetable.widget

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.decodeFromUri
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
import kotlinx.serialization.json.Json
import timber.log.Timber

class TimetableWidget : GlanceAppWidget() {

  override val sizeMode = SizeMode.Exact

  override suspend fun provideGlance(context: Context, id: GlanceId) {
    provideContent {

      val size = LocalSize.current
      val prefs = currentState<Preferences>()
      val timetableUri = prefs[TimetableWidgetReceiver.timetableWidgetKey] ?: ""
      val timetable = Json.decodeFromUri<Timetable>(timetableUri)

      GlanceTimetable(
        modifier = GlanceModifier
          .fillMaxSize(),
        size = size.width,
        timetable = timetable,
      )
    }

  }
}

