package com.suwiki.core.model.user

data class LoggedInUser(
    val userId: String = "",
    val point: Int = 0,
    val writtenEvaluation: Int = 0,
    val writtenExam: Int = 0,
    val viewExam: Int = 0,
    val email: String = "",
)
