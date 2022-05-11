package com.kunize.uswtimetable.ui.home.timetable

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.home.timetable.BitmapConverter.stringToBitmap
import com.kunize.uswtimetable.ui.start.StartActivity
import com.kunize.uswtimetable.util.TimeTableSelPref
import java.lang.Exception

/**
 * Implementation of App Widget functionality.
 */
class TimeTableWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        val ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, TimeTableWidget::class.java))
        val myWidget = TimeTableWidget()
        Log.d("widget", "업데이트 실행")
        myWidget.onUpdate(context, AppWidgetManager.getInstance(context), ids)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.time_table_widget)

    val strBit = TimeTableSelPref.prefs.getString("image", "")
    try {
        val bitmap = stringToBitmap(strBit)
        views.setImageViewBitmap(R.id.widgetImage, bitmap)
    } catch (e: Exception) {

    }

    val intent = Intent(context, StartActivity::class.java) //실행할 액티비티의 클래스
    val pendingIntent = PendingIntent.getActivity(context, 0, intent,  PendingIntent.FLAG_MUTABLE);
    views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}