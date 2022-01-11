package com.kunize.uswtimetable.login

data class LoginFormState(
    val idError: Int? = null,
    val pwError: Int? = null,
    val isDataValid: Boolean = false
)
