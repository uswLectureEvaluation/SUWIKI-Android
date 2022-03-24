package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckEmailFormat(
    @SerializedName("email") val email: String
): Serializable
