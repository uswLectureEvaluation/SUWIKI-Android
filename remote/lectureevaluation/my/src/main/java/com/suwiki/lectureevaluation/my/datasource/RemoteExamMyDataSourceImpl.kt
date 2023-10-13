package com.suwiki.lectureevaluation.my.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamMyDataSource
import com.suwiki.lectureevaluation.my.api.ExamMyApi
import com.suwiki.lectureevaluation.my.response.toModel
import com.suwiki.model.LectureExam
import com.suwiki.model.PurchaseHistory
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteExamMyDataSourceImpl @Inject constructor(
    private val examApi: ExamMyApi,
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
