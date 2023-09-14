package com.suwiki.data.datasource.remote

import com.suwiki.model.Result

interface RemoteExamReportDataSource {

    suspend fun reportExam(
        evaluateIdx: Long,
        content: String = "",
    ): Result<Unit>
}
