package com.suwiki.domain.login.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.login.repository.LoginRepository
import javax.inject.Inject

class FindPasswordUseCase @Inject constructor(
  private val loginRepository: LoginRepository,
) {
  suspend operator fun invoke(
    loginId: String,
    email: String,
  ): Result<Unit> = runCatchingIgnoreCancelled {
    loginRepository.findPassword(
      loginId = loginId,
      email = email,
    )
  }
}
