package com.suwiki.domain.lectureevaluation.usecase.lecture

import com.suwiki.common.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.LectureProviderRepository
import javax.inject.Inject

class GetLectureEvaluationExtraAverageUseCase @Inject constructor(
  private val lectureProviderRepository: LectureProviderRepository,
) {
  suspend operator fun invoke(lectureId: Long): Result<LectureEvaluationExtraAverage> = runCatchingIgnoreCancelled {
    lectureProviderRepository.getLectureEvaluationExtraAverage(
      lectureId = lectureId,
    )
  }
}
