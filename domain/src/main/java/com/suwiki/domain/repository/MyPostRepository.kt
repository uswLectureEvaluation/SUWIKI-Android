package com.suwiki.domain.repository

import com.suwiki.domain.model.LectureExam
import com.suwiki.domain.model.MyEvaluation
import com.suwiki.domain.model.PurchaseHistory
import com.suwiki.domain.model.Result

interface MyPostRepository {
  suspend fun getEvaluation(page: Int): Result<List<MyEvaluation>>

  suspend fun getExamInfos(page: Int): Result<List<LectureExam>>

  suspend fun deleteEvaluation(id: Long)

  suspend fun deleteExamInfo(id: Long)

  suspend fun getPurchaseHistory(): Result<List<PurchaseHistory>>
}
