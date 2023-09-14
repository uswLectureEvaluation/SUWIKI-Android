package com.suwiki.data.datasource.remote

import com.suwiki.model.Result

interface RemoteLectureReportDataSource {

    suspend fun reportLecture(
        evaluateIdx: Long,
        content: String = "",
    ): Result<Unit>
}
