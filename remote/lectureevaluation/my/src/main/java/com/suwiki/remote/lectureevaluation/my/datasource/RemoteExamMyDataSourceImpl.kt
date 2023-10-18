package com.suwiki.remote.lectureevaluation.my.datasource

import com.suwiki.core.model.LectureExam
import com.suwiki.core.model.PurchaseHistory
import com.suwiki.data.datasource.remote.RemoteExamMyDataSource
import com.suwiki.remote.lectureevaluation.my.api.ExamMyApi
import com.suwiki.remote.lectureevaluation.my.response.toModel
import javax.inject.Inject

class RemoteExamMyDataSourceImpl @Inject constructor(
    private val examApi: ExamMyApi,
) : RemoteExamMyDataSource {

    override suspend fun getExamMyPosts(page: Int): List<LectureExam> {
        return examApi.getExamPosts(page).getOrThrow().data.map { it.toModel() }
    }

    override suspend fun getPurchaseHistory(): List<PurchaseHistory> {
        return examApi.getPurchaseHistory().getOrThrow().data.map { it.toModel() }
    }
}
