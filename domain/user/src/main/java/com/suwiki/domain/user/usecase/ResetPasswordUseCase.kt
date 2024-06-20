package com.suwiki.domain.user.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
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
