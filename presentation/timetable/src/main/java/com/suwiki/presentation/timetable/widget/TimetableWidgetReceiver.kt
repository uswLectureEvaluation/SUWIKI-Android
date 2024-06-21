package com.suwiki.presentation.timetable.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.domain.timetable.usecase.GetMainTimetableUseCase
import com.suwiki.presentation.common.ui.extension.encodeToUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class TimetableWidgetReceiver : GlanceAppWidgetReceiver() {
  override val glanceAppWidget: GlanceAppWidget = TimetableWidget()

  @Inject
  lateinit var getMainTimetableUseCase: GetMainTimetableUseCase

  private fun observeData(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
      if (::getMainTimetableUseCase.isInitialized.not()) return@launch

      val timetable = getMainTimetableUseCase().getOrNull() ?: Timetable()

      val timetableUri = Json.encodeToUri(timetable)
      val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(TimetableWidget::class.java)
      glanceIds.forEach { glanceId ->
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
          prefs.toMutablePreferences().apply {
            this[timetableWidgetKey] = timetableUri
          }
        }
      }
      glanceAppWidget.updateAll(context)
    }
  }

  override fun onUpdate(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray,
  ) {
    observeData(context)
    super.onUpdate(context, appWidgetManager, appWidgetIds)
  }

  override fun onReceive(context: Context, intent: Intent) {
    observeData(context)
    super.onReceive(context, intent)
  }

  companion object {
    val timetableWidgetKey = stringPreferencesKey("[key] is timetable widget")
  }
}
