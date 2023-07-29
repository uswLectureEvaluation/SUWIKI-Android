package com.suwiki.model

data class User(
    val userId: String,
    val email: String,
    val point: Int,
    val writtenEvaluation: Int,
    val writtenExam: Int,
    val viewExam: Int,
)
