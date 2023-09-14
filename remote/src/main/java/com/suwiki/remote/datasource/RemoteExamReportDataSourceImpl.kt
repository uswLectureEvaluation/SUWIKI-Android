package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteExamReportDataSource
import com.suwiki.model.Result
import com.suwiki.remote.api.ExamApi
import com.suwiki.remote.request.exam.ReportExamRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteExamReportDataSourceImpl @Inject constructor(
    private val examApi: ExamApi,
) : RemoteExamReportDataSource {

    override suspend fun reportExam(
        evaluateIdx: Long,
        content: String,
    ): Result<Unit> {
        return examApi.reportExam(
            ReportExamRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}
