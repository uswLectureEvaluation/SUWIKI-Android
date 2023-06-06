package com.kunize.uswtimetable.retrofit

import com.google.gson.JsonElement
import com.kunize.uswtimetable.data.remote.BlacklistDto
import com.kunize.uswtimetable.data.remote.DataDto
import com.kunize.uswtimetable.data.remote.LectureDetailEvaluationDto
import com.kunize.uswtimetable.data.remote.LectureDetailExamDto
import com.kunize.uswtimetable.data.remote.LectureDetailInfoDataDto
import com.kunize.uswtimetable.data.remote.LectureEvaluationEditDto
import com.kunize.uswtimetable.data.remote.LectureEvaluationPostDto
import com.kunize.uswtimetable.data.remote.LectureExamDto
import com.kunize.uswtimetable.data.remote.LectureMain
import com.kunize.uswtimetable.data.remote.MajorType
import com.kunize.uswtimetable.data.remote.OpenMajorList
import com.kunize.uswtimetable.data.remote.OpenMajorVersion
import com.kunize.uswtimetable.data.remote.ReportExam
import com.kunize.uswtimetable.data.remote.ReportLecture
import com.kunize.uswtimetable.data.remote.SuspensionHistory
import com.kunize.uswtimetable.data.remote.TokenAuthenticator.Companion.AUTH_HEADER
import com.kunize.uswtimetable.dataclass.CheckEmailFormat
import com.kunize.uswtimetable.dataclass.CheckIdFormat
import com.kunize.uswtimetable.dataclass.EmailDto
import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.dataclass.OverlapCheckDto
import com.kunize.uswtimetable.dataclass.PurchaseHistory
import com.kunize.uswtimetable.dataclass.ResetPasswordDto
import com.kunize.uswtimetable.dataclass.SignUpFormat
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import com.kunize.uswtimetable.dataclass.Token
import com.kunize.uswtimetable.dataclass.UserDataDto
import com.kunize.uswtimetable.dataclass.UserIdEmail
import com.kunize.uswtimetable.util.API.BAN_REASON
import com.kunize.uswtimetable.util.API.BLACKLIST_REASON
import com.kunize.uswtimetable.util.API.BOOKMARK
import com.kunize.uswtimetable.util.API.BUY_EXAM
import com.kunize.uswtimetable.util.API.DELETE_EVALUATE_POST
import com.kunize.uswtimetable.util.API.DELETE_EXAM_POSTS
import com.kunize.uswtimetable.util.API.EDIT_LECTURE_EVALUATION
import com.kunize.uswtimetable.util.API.EDIT_LECTURE_EXAM
import com.kunize.uswtimetable.util.API.EVALUATE_POST
import com.kunize.uswtimetable.util.API.EXAM_POSTS
import com.kunize.uswtimetable.util.API.ID
import com.kunize.uswtimetable.util.API.LECTURE_DETAIL_EVALUATION
import com.kunize.uswtimetable.util.API.LECTURE_DETAIL_EXAM
import com.kunize.uswtimetable.util.API.LECTURE_DETAIL_INFO
import com.kunize.uswtimetable.util.API.LECTURE_MAIN
import com.kunize.uswtimetable.util.API.LOGIN
import com.kunize.uswtimetable.util.API.MY_PAGE
import com.kunize.uswtimetable.util.API.NOTICE
import com.kunize.uswtimetable.util.API.NOTICE_LIST
import com.kunize.uswtimetable.util.API.OPEN_MAJOR_LIST_UPDATE
import com.kunize.uswtimetable.util.API.OPEN_MAJOR_VERSION
import com.kunize.uswtimetable.util.API.PASSWORD
import com.kunize.uswtimetable.util.API.PASSWORD_RESET
import com.kunize.uswtimetable.util.API.PURCHASE_HISTORY
import com.kunize.uswtimetable.util.API.QUIT
import com.kunize.uswtimetable.util.API.REPORT_EVALUATION
import com.kunize.uswtimetable.util.API.REPORT_EXAM
import com.kunize.uswtimetable.util.API.REQUEST_REFRESH
import com.kunize.uswtimetable.util.API.SEARCH
import com.kunize.uswtimetable.util.API.SIGN_UP
import com.kunize.uswtimetable.util.API.SIGN_UP_EMAIL_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_ID_CHECK
import com.kunize.uswtimetable.util.API.WRITE_LECTURE_EVALUATION
import com.kunize.uswtimetable.util.API.WRITE_LECTURE_EXAM
import com.skydoves.sandwich.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface IRetrofit {

    // Refresh Token
    @POST(REQUEST_REFRESH)
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): Call<Token>

    // 회원가입 요청 API
    @POST(SIGN_UP)
    suspend fun signUp(@Body info: SignUpFormat): Response<SuccessCheckDto>

    // 아이디 중복 확인 요청 API
    @POST(SIGN_UP_ID_CHECK)
    suspend fun checkId(@Body loginId: CheckIdFormat): Response<OverlapCheckDto>

    // 이메일 중복 확인 요청 API
    @POST(SIGN_UP_EMAIL_CHECK)
    suspend fun checkEmail(@Body email: CheckEmailFormat): Response<OverlapCheckDto>

    // 공지사항 리스트 API
    @GET(NOTICE_LIST)
    suspend fun getNoticeList(@Query("page") page: Int): Response<DataDto<List<NoticeDto>>>

    // 공지사항 API
    @GET(NOTICE)
    suspend fun getNotice(@Query("noticeId") id: Long): Response<DataDto<NoticeDetailDto>>

    // 아이디 찾기 API
    @POST(ID)
    suspend fun findId(@Body email: EmailDto): ApiResponse<SuccessCheckDto>

    // 비밀번호 찾기(임시 비밀번호 전송) API
    @POST(PASSWORD)
    suspend fun findPassword(@Body info: UserIdEmail): ApiResponse<SuccessCheckDto>

    // 비밀번호 재설정 API
    @POST(PASSWORD_RESET)
    suspend fun resetPassword(@Body passwords: ResetPasswordDto): Response<SuccessCheckDto>

    // 로그인 요청 API
    @POST(LOGIN)
    suspend fun login(@Body info: LoginIdPassword): Response<Token>

    // 회원탈퇴 요청 API
    @POST(QUIT)
    suspend fun quit(@Body info: LoginIdPassword): Response<SuccessCheckDto>

    // 내 정보 페이지 호출 API
    @GET(MY_PAGE)
    suspend fun getUserData(): Response<UserDataDto>

    // 내가 쓴 글 (강의평가)
    @GET(EVALUATE_POST)
    suspend fun getEvaluatePosts(@Query("page") page: Int): Response<DataDto<List<MyEvaluationDto>>>

    // 내가 쓴 글 (시험 정보)
    @GET(EXAM_POSTS)
    suspend fun getExamPosts(@Query("page") page: Int): Response<DataDto<List<LectureExamDto>>>

    @DELETE(DELETE_EVALUATE_POST)
    suspend fun deleteEvaluation(@Query("evaluateIdx") id: Long)

    @DELETE(DELETE_EXAM_POSTS)
    suspend fun deleteExamInfo(@Query("examIdx") id: Long)

    // 시험정보 구매 이력
    @GET(PURCHASE_HISTORY)
    suspend fun getPurchaseHistory(): Response<DataDto<List<PurchaseHistory>>>

    // 이용제한 내역 조회
    @GET(BAN_REASON)
    suspend fun getSuspensionHistory(): ApiResponse<List<SuspensionHistory>>

    // 블랙리스트 내역 조회
    @GET(BLACKLIST_REASON)
    suspend fun getBlacklistHistory(): ApiResponse<List<BlacklistDto>>

    // 검색결과 자세히 보기 (LECTURE)
    @GET(LECTURE_DETAIL_INFO)
    suspend fun getLectureDetailInfo(@Query("lectureId") lectureId: Long): Response<DataDto<LectureDetailInfoDataDto>>

    // 검색결과 자세히 보기 (Evaluation)
    @GET(LECTURE_DETAIL_EVALUATION)
    suspend fun getLectureDetailEvaluation(
        @Query("lectureId") lectureId: Long,
        @Query("page") page: Int,
    ): Response<LectureDetailEvaluationDto>

    // 검색결과 자세히 보기 (Exam)
    @GET(LECTURE_DETAIL_EXAM)
    suspend fun getLectureDetailExam(
        @Query("lectureId") lectureId: Long,
        @Query("page") page: Int,
    ): Response<LectureDetailExamDto>

    // 검색결과 페이지 API(강의평)
    @GET(SEARCH)
    suspend fun getSearchResultDetail(
        @Query("searchValue") searchValue: String,
        @Query("option") option: String,
        @Query("page") page: Int,
        @Query("majorType") majorType: String,
    ): Response<DataDto<MutableList<LectureMain?>>>

    // 메인 페이지
    @GET(LECTURE_MAIN)
    suspend fun getLectureMainList(
        @Query("option") option: String,
        @Query("page") page: Int = 1,
        @Query("majorType") majorType: String = "",
    ): Response<DataDto<MutableList<LectureMain?>>>

    // 강의평가 쓰기
    @POST(WRITE_LECTURE_EVALUATION)
    suspend fun postLectureEvaluation(
        @Query("lectureId") lectureId: Long,
        @Body info: LectureEvaluationPostDto,
    ): Response<String>

    // 시험정보 쓰기
    @POST(WRITE_LECTURE_EXAM)
    suspend fun postLectureExam(
        @Query("lectureId") lectureId: Long,
        @Body info: LectureExamDto,
    ): Response<String>

    // 강의평가 수정
    @PUT(EDIT_LECTURE_EVALUATION)
    suspend fun updateLectureEvaluation(
        @Query("evaluateIdx") lectureId: Long,
        @Body info: LectureEvaluationEditDto,
    ): Response<String>

    // 시험 정보 구매
    @POST(BUY_EXAM)
    suspend fun buyExam(@Query("lectureId") lectureId: Long): Response<String>

    // 시험 정보 수정
    @PUT(EDIT_LECTURE_EXAM)
    suspend fun updateLectureExam(
        @Query("examIdx") lectureId: Long,
        @Body info: LectureExamDto,
    ): Response<String>

    @GET(OPEN_MAJOR_VERSION)
    suspend fun getOpenMajorVersion(): ApiResponse<OpenMajorVersion>

    @GET(OPEN_MAJOR_LIST_UPDATE)
    suspend fun getOpenMajorList(): ApiResponse<OpenMajorList>

    @POST(BOOKMARK)
    suspend fun bookmarkMajor(
        @Body majorName: MajorType,
    ): Response<String>

    @GET(BOOKMARK)
    suspend fun getBookmarkMajorList(): Response<OpenMajorList>

    @DELETE(BOOKMARK)
    suspend fun clearBookmarkMajor(
        @Query("majorType") majorName: String,
    ): Response<String>

    @POST(REPORT_EVALUATION)
    suspend fun reportLecture(
        @Body body: ReportLecture,
    ): ApiResponse<JsonElement>

    @POST(REPORT_EXAM)
    suspend fun reportExam(
        @Body body: ReportExam,
    ): ApiResponse<JsonElement>
}
