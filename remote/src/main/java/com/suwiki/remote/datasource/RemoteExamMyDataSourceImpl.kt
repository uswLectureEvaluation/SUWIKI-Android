package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteExamMyDataSource
import com.suwiki.model.LectureExam
import com.suwiki.model.PurchaseHistory
import com.suwiki.model.Result
import com.suwiki.remote.api.ExamApi
import com.suwiki.remote.response.exam.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteExamMyDataSourceImpl @Inject constructor(
    private val examApi: ExamApi,
) : RemoteExamMyDataSource {

    override suspend fun getExamMyPosts(page: Int): Result<List<LectureExam>> {
        return examApi.getExamPosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getPurchaseHistory(): Result<List<PurchaseHistory>> {
        return examApi.getPurchaseHistory().toResult()
            .map { result -> result.data.map { it.toModel() } }
    }
}
