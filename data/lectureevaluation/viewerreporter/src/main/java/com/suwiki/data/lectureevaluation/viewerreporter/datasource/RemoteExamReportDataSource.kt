package com.suwiki.data.lectureevaluation.viewerreporter.datasource

interface RemoteExamReportDataSource {

  suspend fun reportExam(
    evaluateIdx: Long,
    content: String = "",
  )
}
