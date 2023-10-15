package com.suwiki.lectureevaluation.report.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamReportDataSource
import com.suwiki.model.Result
import com.suwiki.lectureevaluation.report.api.ExamReportApi
import com.suwiki.lectureevaluation.report.request.ReportExamRequest
import javax.inject.Inject

class RemoteExamReportDataSourceImpl @Inject constructor(
    private val examApi: ExamReportApi,
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
