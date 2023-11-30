package com.suwiki.domain.user.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class FindPasswordUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(
    loginId: String,
    email: String,
  ): Result<Unit> = runCatchingIgnoreCancelled {
    userRepository.findPassword(
      loginId = loginId,
      email = email,
    )
  }
}
