package com.kunize.uswtimetable.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.startActivity(
    finishCallingActivity: Boolean = false,
    block: (Intent.() -> Unit),
) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
    if (finishCallingActivity && this is Activity) finish()
}
