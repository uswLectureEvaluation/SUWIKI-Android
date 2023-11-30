package com.suwiki.domain.signup.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.signup.repository.SignUpRepository
import javax.inject.Inject

class CheckIdOverlapUseCase @Inject constructor(
  private val signUpRepository: SignUpRepository,
) {
  suspend operator fun invoke(id: String): Result<Boolean> = runCatchingIgnoreCancelled {
    signUpRepository.checkIdOverlap(loginId = id)
  }
}
