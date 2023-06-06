package com.kunize.uswtimetable.domain.usecase

import com.kunize.uswtimetable.domain.di.UserRepositoryAll
import com.kunize.uswtimetable.domain.repository.UserRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    @UserRepositoryAll private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.login()
    }
}
