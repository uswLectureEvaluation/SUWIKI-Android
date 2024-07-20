package com.suwiki.domain.lectureevaluation.usecase.exam

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.ExamProviderRepository
import javax.inject.Inject

class BuyExamUseCase @Inject constructor(
  private val examProviderRepository: ExamProviderRepository,
) {
  suspend operator fun invoke(lectureId: Long): Result<Unit> = runCatchingIgnoreCancelled {
    examProviderRepository.buyExam(lectureId = lectureId)
  }
}
