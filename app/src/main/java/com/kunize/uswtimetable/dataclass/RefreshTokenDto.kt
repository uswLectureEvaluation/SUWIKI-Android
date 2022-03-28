package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RefreshTokenDto(
    @SerializedName("RefreshToken") val refreshToken: String
): Serializable