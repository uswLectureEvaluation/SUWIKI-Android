package com.suwiki.domain.usecase

import com.suwiki.domain.repository.UserRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.setUserInfo()
    }
}
