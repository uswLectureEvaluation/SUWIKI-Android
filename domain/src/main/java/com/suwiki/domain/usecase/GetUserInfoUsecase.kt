package com.suwiki.domain.usecase

import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUsecase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<LoggedInUser?> {
        return userRepository.userInfo
    }

    fun isLoggedIn(): Flow<Boolean> {
        return userRepository.isLoggedIn
    }
}
