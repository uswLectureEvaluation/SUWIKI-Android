package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationList
import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureProviderRepository
import javax.inject.Inject

class GetLectureEvaluationListUseCase @Inject constructor(
  private val lectureProviderRepository: LectureProviderRepository,
) {
  suspend operator fun invoke(param: Param): Result<LectureEvaluationList> = runCatchingIgnoreCancelled {
    param.run {
      lectureProviderRepository.getLectureEvaluationList(
        lectureId = lectureId,
        page = page,
      )
    }
  }

  data class Param(
    val lectureId: Long,
    val page: Int,
  )
}
