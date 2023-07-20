package com.suwiki.domain.repository

import com.suwiki.domain.model.Result

interface FindPwRepository {
    suspend fun findPw(id: String, email: String): Result<Boolean>
}
