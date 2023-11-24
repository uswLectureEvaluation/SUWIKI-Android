package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamProviderRepository
import javax.inject.Inject

class GetExamDetailListUseCase @Inject constructor(
  private val examProviderRepository: ExamProviderRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    param.run {
      examProviderRepository.getExamDetailList(
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
