package com.suwiki.data.user.datasource

interface LocalUserStorageDataSource {
    suspend fun login(
        userId: String,
        point: Int,
        writtenEvaluation: Int,
        writtenExam: Int,
        viewExam: Int,
        email: String,
        accessToken: String,
        refreshToken: String,
    )

    suspend fun logout()
}
