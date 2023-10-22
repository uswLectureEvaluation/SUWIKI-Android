package com.suwiki.data.lectureevaluation.report.datasource

interface RemoteExamReportDataSource {

  suspend fun reportExam(
    evaluateIdx: Long,
    content: String = "",
  )
}
