package com.suwiki.domain.lectureevaluation.repository

interface LectureReportRepository {

  suspend fun reportLecture(
    evaluateIdx: Long,
    content: String = "",
  )
}
