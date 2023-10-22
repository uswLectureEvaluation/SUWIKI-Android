package com.suwiki.data.datasource.remote

import com.suwiki.core.model.lectureevaluation.LectureExam
import com.suwiki.core.model.lectureevaluation.PurchaseHistory

interface RemoteExamMyDataSource {

  suspend fun getExamMyPosts(page: Int): List<LectureExam>

  suspend fun getPurchaseHistory(): List<PurchaseHistory>
}
