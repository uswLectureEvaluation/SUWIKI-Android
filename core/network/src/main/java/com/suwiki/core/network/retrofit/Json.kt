package com.suwiki.core.network.retrofit

import kotlinx.serialization.json.Json

object Json {
  private val json = Json { ignoreUnknownKeys = true }

  fun getSuwikiErrorBody(body: String) = json.decodeFromString<SuwikiErrorResponse>(body)
}
