package com.suwiki.core.network.retrofit

import kotlinx.serialization.json.Json

object KotlinSerializationUtil {
  val json = Json { ignoreUnknownKeys = true }
}
