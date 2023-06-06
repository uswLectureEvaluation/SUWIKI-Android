package com.kunize.uswtimetable.domain.usecase

import com.kunize.uswtimetable.domain.di.UserRepositoryLogout
import com.kunize.uswtimetable.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUsecase @Inject constructor(
    @UserRepositoryLogout private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.logout()
    }
}
