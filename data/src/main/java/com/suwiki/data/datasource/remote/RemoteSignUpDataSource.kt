package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result
import com.suwiki.core.model.Suspension
import com.suwiki.core.model.Token
import com.suwiki.core.model.User

interface RemoteSignUpDataSource {
    suspend fun signUp(
        id: String,
        password: String,
        email: String,
    ): Result<Boolean>

    suspend fun checkIdOverlap(loginId: String): Result<Boolean>

    suspend fun checkEmailOverlap(email: String): Result<Boolean>
}
