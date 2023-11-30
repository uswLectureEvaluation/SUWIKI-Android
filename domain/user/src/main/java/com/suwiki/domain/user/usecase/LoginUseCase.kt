package com.suwiki.domain.user.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(
    loginId: String,
    password: String,
  ): Result<Unit> = runCatchingIgnoreCancelled {
    userRepository.login(
      loginId = loginId,
      password = password,
    )
  }
}
