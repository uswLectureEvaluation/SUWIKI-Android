package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName

data class EmailCheckDto(
    @SerializedName("success") val success: Boolean
)
