package com.suwiki.domain.openmajor.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.openmajor.repository.OpenMajorRepository
import javax.inject.Inject

class UnRegisterBookmarkUseCase @Inject constructor(
  private val openMajorRepository: OpenMajorRepository,
) {
  suspend operator fun invoke(major: String): Result<Unit> = runCatchingIgnoreCancelled {
    openMajorRepository.removeBookmarkMajor(
      major,
    )
  }
}
