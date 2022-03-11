package com.kunize.uswtimetable.ui.signup

data class SignUpFormState(
    val idError: Int? = null,
    val pwError: Int? = null,
    val pwAgainError: Int? = null,
    val certNumError: Int? = null,
    val isTermChecked: Int? = null,
    val hasBlank: Int? = null,
    val isDataValid: Boolean = false
)