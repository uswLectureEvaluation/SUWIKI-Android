package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result

interface RemoteLectureReportDataSource {

    suspend fun reportLecture(
        evaluateIdx: Long,
        content: String = "",
    ): com.suwiki.core.model.Result<Unit>
}
