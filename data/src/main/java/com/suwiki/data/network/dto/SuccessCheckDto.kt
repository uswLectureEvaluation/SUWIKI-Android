package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SuccessCheckDto(
  @SerializedName("success") val success: Boolean,
) : Serializable
