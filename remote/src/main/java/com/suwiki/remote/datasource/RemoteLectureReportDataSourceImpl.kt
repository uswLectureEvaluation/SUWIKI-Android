package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteLectureReportDataSource
import com.suwiki.model.Result
import com.suwiki.remote.api.LectureApi
import com.suwiki.remote.request.evaluation.ReportLectureRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteLectureReportDataSourceImpl @Inject constructor(
    private val lectureApi: LectureApi,
) : RemoteLectureReportDataSource {

    override suspend fun reportLecture(evaluateIdx: Long, content: String): Result<Unit> {
        return lectureApi.reportLecture(
            ReportLectureRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}
