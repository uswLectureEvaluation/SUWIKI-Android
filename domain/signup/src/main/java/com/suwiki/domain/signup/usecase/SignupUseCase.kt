package com.suwiki.domain.signup.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.signup.repository.SignupRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
  private val signupRepository: SignupRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    signupRepository.signUp(
      id = param.id,
      password = param.password,
      email = param.email,
    )
  }

  data class Param(
    val id: String,
    val password: String,
    val email: String,
  )
}
