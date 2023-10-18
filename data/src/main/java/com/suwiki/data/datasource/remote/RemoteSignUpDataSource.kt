package com.suwiki.data.datasource.remote

interface RemoteSignUpDataSource {
    suspend fun signUp(
        id: String,
        password: String,
        email: String,
    ): Boolean

    suspend fun checkIdOverlap(loginId: String): Boolean

    suspend fun checkEmailOverlap(email: String): Boolean
}
