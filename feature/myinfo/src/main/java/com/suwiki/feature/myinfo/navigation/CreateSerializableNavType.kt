package com.suwiki.feature.myinfo.navigation

// import android.os.Bundle
// import androidx.navigation.NavType
// import kotlinx.serialization.Serializable
// import kotlinx.serialization.json.Json

// inline fun <reified T : Serializable> createSerializableNavType(
//  isNullableAllowed: Boolean = false
// ): NavType<T> {
//  return object : NavType<T>(isNullableAllowed) {
//    override val name: String
//      get() = "SupportSerializable"
//
//    override fun put(bundle: Bundle, key: String, value: T) {
//      bundle.putSerializable(key, value)
//    }
//
//    override fun get(bundle: Bundle, key: String): T? {
//      return bundle.getSerializable(key) as? T
//    }
//
//    override fun parseValue(value: String): T {
//      return Json.decodeFromString(value)
//    }
//  }
// }
