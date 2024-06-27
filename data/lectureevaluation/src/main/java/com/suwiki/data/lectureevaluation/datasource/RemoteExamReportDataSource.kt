package com.suwiki.data.lectureevaluation.datasource

interface RemoteExamReportDataSource {

  suspend fun reportExam(
    examIdx: Long,
    content: String = "",
  )
}
