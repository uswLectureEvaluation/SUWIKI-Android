package com.suwiki.remote.lectureevaluation.my.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamMyDataSource
import com.suwiki.remote.lectureevaluation.my.api.ExamMyApi
import com.suwiki.remote.lectureevaluation.my.response.toModel
import com.suwiki.core.model.LectureExam
import com.suwiki.core.model.PurchaseHistory
import com.suwiki.core.model.Result
import javax.inject.Inject

class RemoteExamMyDataSourceImpl @Inject constructor(
    private val examApi: ExamMyApi,
) : RemoteExamMyDataSource {

    override suspend fun getExamMyPosts(page: Int): com.suwiki.core.model.Result<List<com.suwiki.core.model.LectureExam>> {
        return examApi.getExamPosts(page).toResult()
            .map { result -> result.data.map { it.toModel() } }
    }

    override suspend fun getPurchaseHistory(): com.suwiki.core.model.Result<List<com.suwiki.core.model.PurchaseHistory>> {
        return examApi.getPurchaseHistory().toResult()
            .map { result -> result.data.map { it.toModel() } }
    }
}
