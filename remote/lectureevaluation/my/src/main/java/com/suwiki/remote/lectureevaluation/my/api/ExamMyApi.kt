package com.suwiki.remote.lectureevaluation.my.api

import com.suwiki.core.network.retrofit.ApiResult
import com.suwiki.remote.lectureevaluation.my.response.DataResponse
import com.suwiki.remote.lectureevaluation.my.response.MyExamEvaluationResponse
import com.suwiki.remote.lectureevaluation.my.response.PurchaseHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

// TODO : v2 api로 업그레이드 필요.
interface ExamMyApi {
  companion object {
    const val EXAM_POSTS: String = "/exam-posts"
    const val QUERY_PAGE = "page"
  }

  // 내가 쓴 글 (시험 정보)
  @GET("$EXAM_POSTS/written")
  suspend fun getExamPosts(@Query(QUERY_PAGE) page: Int): ApiResult<DataResponse<List<MyExamEvaluationResponse>>>

  // 시험정보 구매 이력
  @GET("$EXAM_POSTS/purchase")
  suspend fun getPurchaseHistory(): ApiResult<DataResponse<List<PurchaseHistoryResponse>>>
}
