package com.suwiki.core.ui.extension

import android.content.Context
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat

internal inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
  run()
} else {
  this
}

val Context.versionCode: Long
  get() {
    val pi: PackageInfo = packageManager.getPackageInfo(packageName, 0)
    return PackageInfoCompat.getLongVersionCode(pi)
  }
