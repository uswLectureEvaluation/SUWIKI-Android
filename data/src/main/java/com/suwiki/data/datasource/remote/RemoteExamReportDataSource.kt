package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result

interface RemoteExamReportDataSource {

    suspend fun reportExam(
        evaluateIdx: Long,
        content: String = "",
    ): com.suwiki.core.model.Result<Unit>
}
