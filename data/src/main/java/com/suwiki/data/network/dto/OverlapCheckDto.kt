package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OverlapCheckDto(
    @SerializedName("overlap") val overlap: Boolean,
) : Serializable
