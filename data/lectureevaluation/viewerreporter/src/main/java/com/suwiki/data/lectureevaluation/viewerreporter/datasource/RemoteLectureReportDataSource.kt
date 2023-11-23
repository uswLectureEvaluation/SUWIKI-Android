package com.suwiki.data.lectureevaluation.viewerreporter.datasource

interface RemoteLectureReportDataSource {

  suspend fun reportLecture(
    evaluateIdx: Long,
    content: String = "",
  )
}
