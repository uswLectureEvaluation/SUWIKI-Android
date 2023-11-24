package com.suwiki.domain.lectureevaluation.viewerreporter.repository

interface LectureReportRepository {

  suspend fun reportLecture(
    evaluateIdx: Long,
    content: String = "",
  )
}
