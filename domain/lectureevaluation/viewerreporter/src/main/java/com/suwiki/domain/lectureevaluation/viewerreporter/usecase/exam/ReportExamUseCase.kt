package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamReportRepository
import javax.inject.Inject

class ReportExamUseCase @Inject constructor(
  private val examReportRepository: ExamReportRepository,
) {
  suspend operator fun invoke(examIdx: Long): Result<Unit> = runCatchingIgnoreCancelled {
    examReportRepository.reportExam(examIdx = examIdx)
  }
}
