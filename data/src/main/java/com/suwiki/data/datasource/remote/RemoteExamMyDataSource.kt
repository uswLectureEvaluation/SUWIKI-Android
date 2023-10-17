package com.suwiki.data.datasource.remote

import com.suwiki.core.model.LectureDetailExamData
import com.suwiki.core.model.LectureExam
import com.suwiki.core.model.PurchaseHistory
import com.suwiki.core.model.Result

interface RemoteExamMyDataSource {

    suspend fun getExamMyPosts(page: Int): com.suwiki.core.model.Result<List<com.suwiki.core.model.LectureExam>>

    suspend fun getPurchaseHistory(): com.suwiki.core.model.Result<List<com.suwiki.core.model.PurchaseHistory>>
}
