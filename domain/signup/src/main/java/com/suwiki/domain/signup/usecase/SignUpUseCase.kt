package com.suwiki.domain.signup.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.signup.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
  private val signUpRepository: SignUpRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    signUpRepository.signUp(
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
