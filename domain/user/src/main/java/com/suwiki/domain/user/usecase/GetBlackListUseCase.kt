package com.suwiki.domain.user.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.user.Suspension
import com.suwiki.domain.user.repository.UserRepository
import javax.inject.Inject

class GetBlackListUseCase @Inject constructor(
  private val userRepository: UserRepository,
) {
  suspend operator fun invoke(): Result<List<Suspension.Block>> = runCatchingIgnoreCancelled {
    userRepository.getBlackList()
  }
}
