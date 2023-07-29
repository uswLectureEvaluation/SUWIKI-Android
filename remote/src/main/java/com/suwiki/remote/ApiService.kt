package com.suwiki.remote

import com.suwiki.data.db.request.BookmarkMajorRequest
import com.suwiki.data.db.request.CheckEmailRequest
import com.suwiki.data.db.request.CheckIdRequest
import com.suwiki.data.db.request.FindIdRequest
import com.suwiki.data.db.request.FindPasswordRequest
import com.suwiki.data.db.request.LoginRequest
import com.suwiki.data.db.request.QuitRequest
import com.suwiki.data.db.request.ReportExamRequest
import com.suwiki.data.db.request.ReportLectureRequest
import com.suwiki.data.db.request.ResetPasswordRequest
import com.suwiki.data.db.request.SignupRequest
import com.suwiki.data.model.Token
import com.suwiki.data.network.ApiService.Companion.API.BAN_REASON
import com.suwiki.data.network.ApiService.Companion.API.BLACKLIST_REASON
import com.suwiki.data.network.ApiService.Companion.API.BOOKMARK
import com.suwiki.data.network.ApiService.Companion.API.BUY_EXAM
import com.suwiki.data.network.ApiService.Companion.API.DELETE_EVALUATE_POST
import com.suwiki.data.network.ApiService.Companion.API.DELETE_EXAM_POSTS
import com.suwiki.data.network.ApiService.Companion.API.EDIT_LECTURE_EVALUATION
import com.suwiki.data.network.ApiService.Companion.API.EDIT_LECTURE_EXAM
import com.suwiki.data.network.ApiService.Companion.API.EVALUATE_POST
import com.suwiki.data.network.ApiService.Companion.API.EXAM_POSTS
import com.suwiki.data.network.ApiService.Companion.API.ID
import com.suwiki.data.network.ApiService.Companion.API.LECTURE_DETAIL_EVALUATION
import com.suwiki.data.network.ApiService.Companion.API.LECTURE_DETAIL_EXAM
import com.suwiki.data.network.ApiService.Companion.API.LECTURE_DETAIL_INFO
import com.suwiki.data.network.ApiService.Companion.API.LECTURE_MAIN
import com.suwiki.data.network.ApiService.Companion.API.LOGIN
import com.suwiki.data.network.ApiService.Companion.API.MY_PAGE
import com.suwiki.data.network.ApiService.Companion.API.NOTICE
import com.suwiki.data.network.ApiService.Companion.API.NOTICE_LIST
import com.suwiki.data.network.ApiService.Companion.API.OPEN_MAJOR_LIST_UPDATE
import com.suwiki.data.network.ApiService.Companion.API.OPEN_MAJOR_VERSION
import com.suwiki.data.network.ApiService.Companion.API.PASSWORD
import com.suwiki.data.network.ApiService.Companion.API.PASSWORD_RESET
import com.suwiki.data.network.ApiService.Companion.API.PURCHASE_HISTORY
import com.suwiki.data.network.ApiService.Companion.API.QUIT
import com.suwiki.data.network.ApiService.Companion.API.REPORT_EVALUATION
import com.suwiki.data.network.ApiService.Companion.API.REPORT_EXAM
import com.suwiki.data.network.ApiService.Companion.API.REQUEST_REFRESH
import com.suwiki.data.network.ApiService.Companion.API.SEARCH
import com.suwiki.data.network.ApiService.Companion.API.SIGN_UP
import com.suwiki.data.network.ApiService.Companion.API.SIGN_UP_EMAIL_CHECK
import com.suwiki.data.network.ApiService.Companion.API.SIGN_UP_ID_CHECK
import com.suwiki.data.network.ApiService.Companion.API.WRITE_LECTURE_EVALUATION
import com.suwiki.data.network.ApiService.Companion.API.WRITE_LECTURE_EXAM
import com.suwiki.data.network.TokenAuthenticator.Companion.AUTH_HEADER
import com.suwiki.data.network.dto.BlacklistDto
import com.suwiki.data.network.dto.DataDto
import com.suwiki.data.network.dto.LectureDetailEvaluationDataDto
import com.suwiki.data.network.dto.LectureDetailExamDataDto
import com.suwiki.data.network.dto.LectureDetailInfoDto
import com.suwiki.data.network.dto.LectureExamDto
import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.data.network.dto.MyEvaluationDto
import com.suwiki.data.network.dto.NoticeDetailDto
import com.suwiki.data.network.dto.NoticeDto
import com.suwiki.data.network.dto.OpenMajorListDto
import com.suwiki.data.network.dto.OpenMajorVersionDto
import com.suwiki.data.network.dto.OverlapCheckDto
import com.suwiki.data.network.dto.PurchaseHistoryDto
import com.suwiki.data.network.dto.SuccessCheckDto
import com.suwiki.data.network.dto.SuspensionHistoryDto
import com.suwiki.data.network.dto.UserDataDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    // Refresh Token
    @POST(REQUEST_REFRESH)
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): Call<Token>

    // 회원가입 요청 API
    @POST(SIGN_UP)
    suspend fun signUp(@Body signupRequest: SignupRequest): ApiResult<SuccessCheckDto>

    // 아이디 중복 확인 요청 API
    @POST(SIGN_UP_ID_CHECK)
    suspend fun checkId(@Body checkIdRequest: CheckIdRequest): ApiResult<OverlapCheckDto>

    // 이메일 중복 확인 요청 API
    @POST(SIGN_UP_EMAIL_CHECK)
    suspend fun checkEmail(@Body checkEmailRequest: CheckEmailRequest): ApiResult<OverlapCheckDto>

    // 공지사항 리스트 API
    @GET(NOTICE_LIST)
    suspend fun getNoticeList(@Query("page") page: Int): ApiResult<DataDto<List<NoticeDto>>>

    // 공지사항 API
    @GET(NOTICE)
    suspend fun getNotice(@Query("noticeId") id: Long): ApiResult<DataDto<NoticeDetailDto>>

    // 아이디 찾기 API
    @POST(ID)
    suspend fun findId(@Body findIdRequest: FindIdRequest): ApiResult<SuccessCheckDto>

    // 비밀번호 찾기(임시 비밀번호 전송) API
    @POST(PASSWORD)
    suspend fun findPassword(@Body findPasswordRequest: FindPasswordRequest): ApiResult<SuccessCheckDto>

    // 비밀번호 재설정 API
    @POST(PASSWORD_RESET)
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): ApiResult<SuccessCheckDto>

    // 로그인 요청 API
    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): ApiResult<Token>

    // 회원탈퇴 요청 API
    @POST(QUIT)
    suspend fun quit(@Body quitRequest: QuitRequest): ApiResult<SuccessCheckDto>

    // 내 정보 페이지 호출 API
    @GET(MY_PAGE)
    suspend fun getUserData(): Response<UserDataDto>

    // 내가 쓴 글 (강의평가)
    @GET(EVALUATE_POST)
    suspend fun getEvaluatePosts(@Query("page") page: Int): ApiResult<DataDto<List<MyEvaluationDto>>>

    // 내가 쓴 글 (시험 정보)
    @GET(EXAM_POSTS)
    suspend fun getExamPosts(@Query("page") page: Int): ApiResult<DataDto<List<LectureExamDto>>>

    @DELETE(DELETE_EVALUATE_POST)
    suspend fun deleteEvaluation(@Query("evaluateIdx") id: Long)

    @DELETE(DELETE_EXAM_POSTS)
    suspend fun deleteExamInfo(@Query("examIdx") id: Long)

    // 시험정보 구매 이력
    @GET(PURCHASE_HISTORY)
    suspend fun getPurchaseHistory(): ApiResult<DataDto<List<PurchaseHistoryDto>>>

    // 이용제한 내역 조회
    @GET(BAN_REASON)
    suspend fun getSuspensionHistory(): ApiResult<List<SuspensionHistoryDto>>

    // 블랙리스트 내역 조회
    @GET(BLACKLIST_REASON)
    suspend fun getBlacklistHistory(): ApiResult<List<BlacklistDto>>

    // 검색결과 자세히 보기 (LECTURE)
    @GET(LECTURE_DETAIL_INFO)
    suspend fun getLectureDetailInfo(@Query("lectureId") lectureId: Long): ApiResult<DataDto<LectureDetailInfoDto>>

    // 검색결과 자세히 보기 (Evaluation)
    @GET(LECTURE_DETAIL_EVALUATION)
    suspend fun getLectureDetailEvaluation(
        @Query("lectureId") lectureId: Long,
        @Query("page") page: Int,
    ): ApiResult<LectureDetailEvaluationDataDto>

    // 검색결과 자세히 보기 (Exam)
    @GET(LECTURE_DETAIL_EXAM)
    suspend fun getLectureDetailExam(
        @Query("lectureId") lectureId: Long,
        @Query("page") page: Int,
    ): ApiResult<LectureDetailExamDataDto>

    // 검색결과 페이지 API(강의평)
    @GET(SEARCH)
    suspend fun getSearchResultDetail(
        @Query("searchValue") searchValue: String,
        @Query("option") option: String,
        @Query("page") page: Int,
        @Query("majorType") majorType: String,
    ): ApiResult<DataDto<MutableList<LectureMainDto?>>>

    // 메인 페이지
    @GET(LECTURE_MAIN)
    suspend fun getLectureMainList(
        @Query("option") option: String,
        @Query("page") page: Int = 1,
        @Query("majorType") majorType: String = "",
    ): ApiResult<DataDto<MutableList<LectureMainDto?>>>

    // 강의평가 쓰기
    @POST(WRITE_LECTURE_EVALUATION)
    suspend fun postLectureEvaluation(
        @Field("lectureName") lectureName: String,
        @Field("professor") professor: String,
        @Field("selectedSemester") selectedSemester: String,
        @Field("satisfaction") satisfaction: Float,
        @Field("learning") learning: Float,
        @Field("honey") honey: Float,
        @Field("team") team: Int,
        @Field("difficulty") difficulty: Int,
        @Field("homework") homework: Int,
        @Field("content") content: String,
    ): Response<String>

    // 시험정보 쓰기
    @POST(WRITE_LECTURE_EXAM)
    suspend fun postLectureExam(
        @Query("lectureId") lectureId: Long,
        @Field("lectureName") lectureName: String?,
        @Field("professor") professor: String?,
        @Field("selectedSemester") selectedSemester: String?,
        @Field("examInfo") examInfo: String,
        @Field("examType") examType: String?,
        @Field("examDifficulty") examDifficulty: String,
        @Field("content") content: String,
    ): Response<String>

    // 강의평가 수정
    @PUT(EDIT_LECTURE_EVALUATION)
    suspend fun updateLectureEvaluation(
        @Query("evaluateIdx") lectureId: Long,
        @Field("selectedSemester") selectedSemester: String,
        @Field("satisfaction") satisfaction: Float,
        @Field("learning") learning: Float,
        @Field("honey") honey: Float,
        @Field("team") team: Int,
        @Field("difficulty") difficulty: Int,
        @Field("homework") homework: Int,
        @Field("content") content: String,
    ): Response<String>

    // 시험 정보 구매
    @POST(BUY_EXAM)
    suspend fun buyExam(@Query("lectureId") lectureId: Long): ApiResult<String>

    // 시험 정보 수정
    @PUT(EDIT_LECTURE_EXAM)
    suspend fun updateLectureExam(
        @Query("examIdx") lectureId: Long,
        @Field("selectedSemester") selectedSemester: String?,
        @Field("examInfo") examInfo: String,
        @Field("examType") examType: String?,
        @Field("examDifficulty") examDifficulty: String,
        @Field("content") content: String,
    ): Response<String>

    @GET(OPEN_MAJOR_VERSION)
    suspend fun getOpenMajorVersion(): ApiResult<OpenMajorVersionDto>

    @GET(OPEN_MAJOR_LIST_UPDATE)
    suspend fun getOpenMajorList(): ApiResult<OpenMajorListDto>

    @POST(BOOKMARK)
    suspend fun bookmarkMajor(@Body bookmarkMajorRequest: BookmarkMajorRequest): ApiResult<String>

    @GET(BOOKMARK)
    suspend fun getBookmarkMajorList(): ApiResult<OpenMajorListDto>

    @DELETE(BOOKMARK)
    suspend fun clearBookmarkMajor(
        @Query("majorType") majorName: String,
    ): ApiResult<String>

    // 강의평가 신고하기
    @POST(REPORT_EVALUATION)
    suspend fun reportLecture(@Body reportLectureRequest: ReportLectureRequest): ApiResult<Unit>

    // 시험정보 신고하기
    @POST(REPORT_EXAM)
    suspend fun reportExam(@Body reportExamRequest: ReportExamRequest): ApiResult<Unit>

    companion object {
        object API {
            const val BASE_URL: String = "https://api.suwiki.kr"
            const val REQUEST_REFRESH: String = "/user/refresh"
            const val NOTICE_LIST: String = "/notice/all"
            const val NOTICE: String = "/notice/"
            const val SEARCH: String = "/lecture/search/"
            const val ID: String = "/user/find-id"
            const val PASSWORD: String = "/user/find-pw"
            const val PASSWORD_RESET: String = "/user/reset-pw"
            const val LOGIN: String = "/user/login"
            const val QUIT: String = "/user/quit"
            const val MY_PAGE: String = "/user/my-page"
            const val BAN_REASON: String = "/user/restricted-reason"
            const val BLACKLIST_REASON: String = "/user/blacklist-reason"
            const val EVALUATE_POST: String = "/evaluate-posts/written"
            const val DELETE_EVALUATE_POST: String = "/evaluate-posts/"
            const val EXAM_POSTS: String = "/exam-posts/written"
            const val DELETE_EXAM_POSTS: String = "/exam-posts/"
            const val PURCHASE_HISTORY: String = "/exam-posts/purchase"
            const val SIGN_UP: String = "/user/join"
            const val SIGN_UP_ID_CHECK: String = "/user/check-id"
            const val SIGN_UP_EMAIL_CHECK: String = "/user/check-email"
            const val LECTURE_MAIN: String = "/lecture/all/"
            const val LECTURE_DETAIL_INFO = "/lecture/"
            const val LECTURE_DETAIL_EVALUATION = "/evaluate-posts/"
            const val LECTURE_DETAIL_EXAM = "/exam-posts/"
            const val WRITE_LECTURE_EVALUATION = "/evaluate-posts/"
            const val WRITE_LECTURE_EXAM = "/exam-posts/"
            const val EDIT_LECTURE_EVALUATION = "/evaluate-posts/"
            const val EDIT_LECTURE_EXAM = "/exam-posts/"
            const val BUY_EXAM = "/exam-posts/purchase/"
            const val OPEN_MAJOR_VERSION = "/suwiki/version/"
            const val OPEN_MAJOR_LIST_UPDATE = "/suwiki/majorType"
            const val BOOKMARK = "/user/favorite-major/"
            const val REPORT_EVALUATION = "/user/report/evaluate"
            const val REPORT_EXAM = "/user/report/exam"
        }
    }
}
