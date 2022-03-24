package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckIdFormat(
    @SerializedName("loginId") val id: String
): Serializable
