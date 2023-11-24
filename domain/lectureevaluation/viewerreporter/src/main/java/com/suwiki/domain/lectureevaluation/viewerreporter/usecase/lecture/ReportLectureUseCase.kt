package com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.ExamReportRepository
import com.suwiki.domain.lectureevaluation.viewerreporter.repository.LectureReportRepository
import javax.inject.Inject

class ReportLectureUseCase @Inject constructor(
  private val lectureReportRepository: LectureReportRepository,
) {
  suspend operator fun invoke(evaluateIdx: Long): Result<Unit> = runCatchingIgnoreCancelled {
    lectureReportRepository.reportLecture(evaluateIdx = evaluateIdx)
  }
}
