package com.suwiki.domain.login.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.login.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
  private val loginRepository: LoginRepository,
) {
  suspend operator fun invoke(
    loginId: String,
    password: String,
  ): Result<Unit> = runCatchingIgnoreCancelled {
    loginRepository.login(
      loginId = loginId,
      password = password,
    )
  }
}
