package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureProviderRepository
import javax.inject.Inject

class RetrieveLectureEvaluationAverageListUseCase @Inject constructor(
  private val lectureProviderRepository: LectureProviderRepository,
) {
  suspend operator fun invoke(param: Param): Result<List<LectureEvaluationAverage?>> = runCatchingIgnoreCancelled {
    param.run {
      lectureProviderRepository.retrieveLectureEvaluationAverageList(
        search = search,
        option = option,
        page = page,
        majorType = majorType,
      )
    }
  }

  data class Param(
    val search: String,
    val option: String,
    val page: Int,
    val majorType: String,
  )
}
