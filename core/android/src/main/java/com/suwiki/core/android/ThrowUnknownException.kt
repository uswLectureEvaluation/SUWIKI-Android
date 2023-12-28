package com.suwiki.core.android

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

fun recordException(
  e: Throwable,
) {
  Firebase.crashlytics.recordException(e)
}
