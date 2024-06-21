package com.suwiki.presentation.common.ui.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat

val Context.versionCode: Long
  get() {
    val pi: PackageInfo = packageManager.getPackageInfo(packageName, 0)
    return PackageInfoCompat.getLongVersionCode(pi)
  }

fun Context.findActivity(): Activity {
  var context = this
  while (context is ContextWrapper) {
    if (context is Activity) return context
    context = context.baseContext
  }
  throw IllegalStateException("no activity")
}
