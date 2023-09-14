package com.suwiki.data.datasource.remote

import com.suwiki.model.LectureDetailExamData
import com.suwiki.model.LectureExam
import com.suwiki.model.PurchaseHistory
import com.suwiki.model.Result

interface RemoteExamMyDataSource {

    suspend fun getExamMyPosts(page: Int): Result<List<LectureExam>>

    suspend fun getPurchaseHistory(): Result<List<PurchaseHistory>>
}
