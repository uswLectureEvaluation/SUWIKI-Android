package com.suwiki.core.android

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.suwiki.core.model.exception.UnknownException

fun recordException(
  e: Throwable,
) {
  Firebase.crashlytics.recordException(e)
}
