package com.kunize.domain.usecase

import com.kunize.domain.Outcome
import com.kunize.domain.model.IdPassword
import com.kunize.domain.model.Jwt
import com.kunize.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idPassword: IdPassword): Flow<Outcome<Jwt>> {
        return authRepository.login(idPassword)
    }
}