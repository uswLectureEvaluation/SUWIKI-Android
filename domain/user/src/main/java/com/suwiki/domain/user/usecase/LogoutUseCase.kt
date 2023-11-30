package com.suwiki.domain.user.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(): Result<Unit> = runCatchingIgnoreCancelled {
    userRepository.logout()
  }
}
