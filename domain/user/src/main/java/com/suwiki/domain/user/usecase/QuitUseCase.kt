package com.suwiki.domain.user.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class QuitUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(
    id: String,
    password: String,
  ): Result<Unit> = runCatchingIgnoreCancelled {
    userRepository.quit(
      id = id,
      password = password,
    )
  }
}
