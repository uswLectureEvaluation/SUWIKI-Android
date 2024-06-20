package com.suwiki.domain.signup.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.signup.repository.SignupRepository
import javax.inject.Inject

class CheckEmailOverlapUseCase @Inject constructor(
  private val signUpRepository: SignupRepository,
) {
  suspend operator fun invoke(email: String): Result<Boolean> = runCatchingIgnoreCancelled {
    signUpRepository.checkEmailOverlap(email = email)
  }
}
