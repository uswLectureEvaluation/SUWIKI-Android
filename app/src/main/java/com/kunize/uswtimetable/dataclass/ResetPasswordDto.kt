package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResetPasswordDto(
//    val currentPassword: String,
    @SerializedName("password") val newPassword: String
): Serializable