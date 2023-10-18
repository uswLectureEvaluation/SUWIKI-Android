package com.suwiki.data.datasource.remote

import com.suwiki.core.model.LectureExam
import com.suwiki.core.model.PurchaseHistory

interface RemoteExamMyDataSource {

    suspend fun getExamMyPosts(page: Int): List<LectureExam>

    suspend fun getPurchaseHistory(): List<PurchaseHistory>
}
