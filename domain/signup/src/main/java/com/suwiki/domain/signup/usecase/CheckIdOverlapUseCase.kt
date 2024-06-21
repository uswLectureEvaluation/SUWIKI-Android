package com.suwiki.domain.signup.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.signup.repository.SignupRepository
import javax.inject.Inject

class CheckIdOverlapUseCase @Inject constructor(
  private val signUpRepository: SignupRepository,
) {
  suspend operator fun invoke(id: String): Result<Boolean> = runCatchingIgnoreCancelled {
    signUpRepository.checkIdOverlap(loginId = id)
  }
}
