package com.suwiki.domain.lectureevaluation.usecase.exam

import com.suwiki.common.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.ExamMyRepository
import javax.inject.Inject

class GetMyExamEvaluationListUseCase @Inject constructor(
  private val examMyRepository: ExamMyRepository,
) {
  suspend operator fun invoke(page: Int): Result<List<MyExamEvaluation>> = runCatchingIgnoreCancelled {
    examMyRepository.getMyExamEvaluationList(page = page)
  }
}
