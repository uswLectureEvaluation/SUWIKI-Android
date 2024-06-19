package com.suwiki.remote.common.retrofit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuwikiErrorResponse(
  val exception: String,
  @SerialName("code") val suwikiCode: String,
  val message: String,
  @SerialName("status") val httpStatusCode: Int,
  val error: String,
)
