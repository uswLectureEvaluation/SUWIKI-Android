package com.suwiki.data.datasource.remote

interface RemoteExamReportDataSource {

  suspend fun reportExam(
    evaluateIdx: Long,
    content: String = "",
  )
}
