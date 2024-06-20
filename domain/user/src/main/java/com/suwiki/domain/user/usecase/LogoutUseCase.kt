package com.suwiki.domain.user.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(): Result<Unit> = runCatchingIgnoreCancelled {
    userRepository.logout()
  }
}
