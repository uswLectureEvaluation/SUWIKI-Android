package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamProviderRepository
import javax.inject.Inject

class BuyExamUseCase @Inject constructor(
  private val examProviderRepository: ExamProviderRepository,
) {
  suspend operator fun invoke(lectureId: Long): Result<Unit> = runCatchingIgnoreCancelled {
    examProviderRepository.buyExam(lectureId = lectureId)
  }
}
