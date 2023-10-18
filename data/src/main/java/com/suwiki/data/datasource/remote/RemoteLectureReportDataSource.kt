package com.suwiki.data.datasource.remote

interface RemoteLectureReportDataSource {

    suspend fun reportLecture(
        evaluateIdx: Long,
        content: String = "",
    )
}
