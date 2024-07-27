package com.suwiki.common.android

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun recordException(
  e: Throwable,
) {
  Timber.e(e)
  Firebase.crashlytics.recordException(e)
}
