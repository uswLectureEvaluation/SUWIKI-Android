package com.kunize.domain.repository

import com.kunize.domain.Outcome
import com.kunize.domain.model.IdPassword
import com.kunize.domain.model.Jwt
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(idPassword: IdPassword): Flow<Outcome<Jwt>>
}