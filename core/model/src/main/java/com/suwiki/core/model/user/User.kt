package com.suwiki.core.model.user

data class User(
    val userId: String,
    val email: String,
    val point: Int,
    val writtenEvaluation: Int,
    val writtenExam: Int,
    val viewExam: Int,
)
