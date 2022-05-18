package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResetPasswordDto(
    @SerializedName("prePassword") val currentPassword: String,
    @SerializedName("newPassword") val newPassword: String
): Serializable