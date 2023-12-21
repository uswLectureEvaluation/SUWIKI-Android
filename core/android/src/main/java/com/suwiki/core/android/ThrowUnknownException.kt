package com.suwiki.core.android

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.suwiki.core.model.exception.UnknownException

fun throwUnknownException(
  e: Throwable,
) {
  Firebase.crashlytics.recordException(e)
  throw e.message?.let { UnknownException(it) } ?: UnknownException()
}
