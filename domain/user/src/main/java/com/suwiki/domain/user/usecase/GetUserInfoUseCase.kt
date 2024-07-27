package com.suwiki.domain.user.usecase

import com.suwiki.common.model.user.User
import com.suwiki.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(): Flow<User> = userRepository.getUserInfo()
}
