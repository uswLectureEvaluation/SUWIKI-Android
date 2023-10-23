package com.suwiki.core.network.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class SuwikiErrorResponse(
  val exception: String,
  val code: String,
  val message: String,
  val status: Int,
  val error: String,
)
