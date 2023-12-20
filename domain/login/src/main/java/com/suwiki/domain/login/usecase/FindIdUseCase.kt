package com.suwiki.domain.login.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.login.repository.LoginRepository
import javax.inject.Inject

class FindIdUseCase @Inject constructor(
  private val loginRepository: LoginRepository,
) {
  suspend operator fun invoke(email: String): Result<Unit> = runCatchingIgnoreCancelled {
    loginRepository.findId(email)
  }
}
