package com.suwiki.data.datasource.local

interface LocalUserStorageDataSource {
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
