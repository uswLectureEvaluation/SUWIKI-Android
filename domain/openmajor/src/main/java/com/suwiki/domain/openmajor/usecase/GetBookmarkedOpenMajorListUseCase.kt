package com.suwiki.domain.openmajor.usecase

import com.suwiki.domain.openmajor.repository.OpenMajorRepository
import javax.inject.Inject

class GetBookmarkedOpenMajorListUseCase @Inject constructor(
  private val openMajorRepository: OpenMajorRepository,
) {
  suspend operator fun invoke(): Result<List<String>> = kotlin.runCatching {
    openMajorRepository.getBookmarkedOpenMajorList()
  }
}
