package com.kunize.uswtimetable.dataclass

import java.io.Serializable

data class SignUpFormat(
    val id: String,
    val password: String,
    val email: String,
): Serializable
