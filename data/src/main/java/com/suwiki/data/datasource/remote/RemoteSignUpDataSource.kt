package com.suwiki.data.datasource.remote

import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User

interface RemoteSignUpDataSource {
    suspend fun signUp(
        id: String,
        password: String,
        email: String,
    ): Result<Boolean>

    suspend fun checkIdOverlap(loginId: String): Result<Boolean>

    suspend fun checkEmailOverlap(email: String): Result<Boolean>
}
