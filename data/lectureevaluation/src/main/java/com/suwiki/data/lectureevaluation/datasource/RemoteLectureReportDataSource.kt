package com.suwiki.data.lectureevaluation.datasource

interface RemoteLectureReportDataSource {

  suspend fun reportLecture(
    evaluateIdx: Long,
    content: String = "",
  )
}
