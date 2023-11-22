package com.suwiki.domain.user.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(
    currentPassword: String,
    newPassword: String,
  ): Result<Unit> = runCatchingIgnoreCancelled {
    userRepository.resetPassword(
      currentPassword = currentPassword,
      newPassword = newPassword,
    )
  }
}
