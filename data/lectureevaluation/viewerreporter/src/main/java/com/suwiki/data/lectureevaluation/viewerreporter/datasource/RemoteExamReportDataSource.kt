package com.suwiki.data.lectureevaluation.viewerreporter.datasource

interface RemoteExamReportDataSource {

  suspend fun reportExam(
    examIdx: Long,
    content: String = "",
  )
}
