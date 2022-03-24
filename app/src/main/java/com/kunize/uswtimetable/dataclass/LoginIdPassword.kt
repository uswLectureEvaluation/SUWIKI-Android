package com.kunize.uswtimetable.dataclass

import java.io.Serializable

data class LoginIdPassword(
    val loginId: String,
    val password: String
): Serializable
