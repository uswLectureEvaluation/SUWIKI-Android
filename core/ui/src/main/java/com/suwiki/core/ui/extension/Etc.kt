package com.suwiki.core.ui.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat

internal inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
  run()
} else {
  this
}
