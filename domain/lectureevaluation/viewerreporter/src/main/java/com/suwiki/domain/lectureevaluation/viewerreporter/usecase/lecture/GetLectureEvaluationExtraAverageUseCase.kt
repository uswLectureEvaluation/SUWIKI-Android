package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureProviderRepository
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
