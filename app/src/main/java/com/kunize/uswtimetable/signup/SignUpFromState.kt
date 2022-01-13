package com.kunize.uswtimetable.signup

data class SignUpFromState(
    val idError: Int? = null,
    val pwError: Int? = null,
    val pwAgainError: Int? = null,
    val certNumError: Int? = null,
    val isDataValid: Boolean = false
)