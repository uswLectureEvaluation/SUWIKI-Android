package com.suwiki.core.ui.util

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.suwiki.core.ui.extension.parcelable
import kotlinx.serialization.json.Json

inline fun <reified T : Parcelable> createParcelableNavType(
  isNullableAllowed: Boolean = false
): NavType<T> {
  return object : NavType<T>(isNullableAllowed) {
    override val name: String
      get() = "SupportParcelable"

    override fun put(bundle: Bundle, key: String, value: T) {
      bundle.putParcelable(key, value) // Bundle에 Parcelable 타입으로 추가
    }

    override fun get(bundle: Bundle, key: String): T? {
      return bundle.parcelable(key) // Bundle에서 Parcelable 타입으로 꺼낸다
    }

    override fun parseValue(value: String): T {
      return Json.decodeFromString(value) // String 전달된 Parsing 방법을 정의
    }
  }
}
