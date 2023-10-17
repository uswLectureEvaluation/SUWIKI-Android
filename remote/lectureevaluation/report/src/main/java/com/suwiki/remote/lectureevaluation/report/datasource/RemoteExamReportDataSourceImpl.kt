package com.suwiki.remote.lectureevaluation.report.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamReportDataSource
import com.suwiki.core.model.Result
import com.suwiki.remote.lectureevaluation.report.api.ExamReportApi
import com.suwiki.remote.lectureevaluation.report.request.ReportExamRequest
import javax.inject.Inject

class RemoteExamReportDataSourceImpl @Inject constructor(
    private val examApi: ExamReportApi,
) : RemoteExamReportDataSource {

    override suspend fun reportExam(
        evaluateIdx: Long,
        content: String,
    ): com.suwiki.core.model.Result<Unit> {
        return examApi.reportExam(
            ReportExamRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}
