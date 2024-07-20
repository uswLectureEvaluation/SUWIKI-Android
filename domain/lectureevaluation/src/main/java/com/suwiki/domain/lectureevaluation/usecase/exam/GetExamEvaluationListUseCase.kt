package com.suwiki.domain.lectureevaluation.usecase.exam

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluationList
import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.ExamProviderRepository
import javax.inject.Inject

class GetExamEvaluationListUseCase @Inject constructor(
  private val examProviderRepository: ExamProviderRepository,
) {
  suspend operator fun invoke(param: Param): Result<ExamEvaluationList> = runCatchingIgnoreCancelled {
    param.run {
      examProviderRepository.getExamEvaluationList(
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
