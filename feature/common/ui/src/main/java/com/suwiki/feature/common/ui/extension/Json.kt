package com.suwiki.feature.common.ui.extension

import android.net.Uri
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> Json.encodeToUri(value: T): String {
  return Uri.encode(encodeToString(value))
}

inline fun <reified T> Json.decodeFromUri(value: String): T {
  return decodeFromString(Uri.decode(value))
}
