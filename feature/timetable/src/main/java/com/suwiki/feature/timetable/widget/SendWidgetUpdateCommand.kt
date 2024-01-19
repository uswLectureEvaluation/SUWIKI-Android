package com.suwiki.feature.timetable.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent

fun sendWidgetUpdateCommand(context: Context) {
    context.sendBroadcast(
        Intent(
            context,
            TimetableWidgetReceiver::class.java
        ).setAction(
            AppWidgetManager.ACTION_APPWIDGET_UPDATE
        )
    )
}
