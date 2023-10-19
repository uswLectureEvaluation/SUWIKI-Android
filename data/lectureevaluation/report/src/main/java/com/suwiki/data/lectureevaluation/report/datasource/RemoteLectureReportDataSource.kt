package com.suwiki.data.lectureevaluation.report.datasource

interface RemoteLectureReportDataSource {

    suspend fun reportLecture(
        evaluateIdx: Long,
        content: String = "",
    )
}
