package com.suwiki.domain.lectureevaluation.usecase.lecture

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.LectureReportRepository
import javax.inject.Inject

class ReportLectureUseCase @Inject constructor(
  private val lectureReportRepository: LectureReportRepository,
) {
  suspend operator fun invoke(evaluateIdx: Long): Result<Unit> = runCatchingIgnoreCancelled {
    lectureReportRepository.reportLecture(evaluateIdx = evaluateIdx)
  }
}
