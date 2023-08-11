package com.suwiki.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    val isLoggedIn: Flow<Boolean>
    val userId: Flow<String?>
    val point: Flow<Int?>
    val writtenEvaluation: Flow<Int?>
    val writtenExam: Flow<Int?>
    val viewExam: Flow<Int?>
    val email: Flow<String?>

    suspend fun login(
        userId: String,
        point: Int,
        writtenEvaluation: Int,
        writtenExam: Int,
        viewExam: Int,
        email: String,
    )

    suspend fun logout()
}
