package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamReportRepository
import javax.inject.Inject

class ReportExamUseCase @Inject constructor(
  private val examReportRepository: ExamReportRepository,
) {
  suspend operator fun invoke(evaluateIdx: Long): Result<Unit> = runCatchingIgnoreCancelled {
    examReportRepository.reportExam(evaluateIdx = evaluateIdx)
  }
}
