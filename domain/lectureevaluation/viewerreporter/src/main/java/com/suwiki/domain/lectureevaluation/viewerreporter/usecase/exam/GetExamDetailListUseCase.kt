package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamProviderRepository
import javax.inject.Inject

class GetExamDetailListUseCase @Inject constructor(
  private val examProviderRepository: ExamProviderRepository,
) {
  suspend operator fun invoke(param: Param): Result<ExamEvaluationList> = runCatchingIgnoreCancelled {
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
