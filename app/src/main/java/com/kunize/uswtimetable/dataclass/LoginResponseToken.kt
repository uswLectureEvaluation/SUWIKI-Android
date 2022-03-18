package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseToken(
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("RefreshToken") val refreshToken: String,
): Serializable
