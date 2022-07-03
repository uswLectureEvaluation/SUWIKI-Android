package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SuccessCheckDto(
    @SerializedName("success") val success: Boolean
) : Serializable
