package com.kunize.uswtimetable.retrofit

import com.google.gson.JsonElement
import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.dataclass.*
import com.kunize.uswtimetable.util.API.BASE_URL
import com.kunize.uswtimetable.util.API.EVALUATE_POST
import com.kunize.uswtimetable.util.API.EXAM
import com.kunize.uswtimetable.util.API.EXAM_POSTS
import com.kunize.uswtimetable.util.API.LECTURE
import com.kunize.uswtimetable.util.API.LOGIN
import com.kunize.uswtimetable.util.API.MY_PAGE
import com.kunize.uswtimetable.util.API.NOTICE
import com.kunize.uswtimetable.util.API.NOTICE_LIST
import com.kunize.uswtimetable.util.API.PASSWORD
import com.kunize.uswtimetable.util.API.PASSWORD_RESET
import com.kunize.uswtimetable.util.API.QUIT
import com.kunize.uswtimetable.util.API.SIGN_UP
import com.kunize.uswtimetable.util.API.SIGN_UP_EMAIL_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_ID_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_SCHOOL_CHECK
import com.kunize.uswtimetable.util.API.UPDATE_EVALUATE_POST
import com.kunize.uswtimetable.util.API.UPDATE_EXAM_POSTS
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRetrofit {

    // 메인 페이지 요청 API
    @GET()
    fun getMain(): Call<JsonElement>

    // 회원가입 요청 API
    @POST(SIGN_UP)
    fun signUp(@Body info: SignUpFormat): Call<Boolean>

    // 아이디 중복 확인 요청 API
    @POST(SIGN_UP_ID_CHECK)
    fun checkId(@Body loginId: String): Call<Boolean>

    // 이메일 중복 확인 요청 API
    @POST(SIGN_UP_EMAIL_CHECK)
    fun checkEmail(@Body email: String): Call<Boolean>

    // 학교 메일 인증 API
    @GET(SIGN_UP_SCHOOL_CHECK)
    fun verifyEmail()

    // 공지사항 리스트 API
    @GET(NOTICE_LIST)
    suspend fun getNoticeList(@Query("page") page: Int?): Call<List<NoticeDto>>

    // 공지사항 API
    @GET(NOTICE)
    suspend fun getNotice(@Query("notice_id") id: Long): Call<NoticeDetailDto>

    // 비밀번호 찾기(임시 비밀번호 전송) API
    @POST(PASSWORD)
    fun findPassword(@Body info: UserIdEmail): Call<Boolean>

    // 비밀번호 재설정 API
    @POST(PASSWORD_RESET)
    fun resetPassword(@Body password: String): Call<Boolean>

    // 로그인 요청 API
    @POST(LOGIN)
    fun login(@Body info: LoginIdPassword): Call<LoginResponseToken>

    // 회원탈퇴 요청 API
    @POST(QUIT)
    fun quit(@Body info: LoginIdPassword): Call<Boolean>

    // 내 정보 페이지 호출 API
    @GET(MY_PAGE)
    fun getUserInfo(): Call<MyPageData>

    // 내가 쓴 글 (강의평가)
    @GET(EVALUATE_POST)
    fun getEvaluatePosts(@Query("page") page: Int): Call<MyEvaluation>

    // 내가 쓴 글 (강의평가 수정)
    @GET(UPDATE_EVALUATE_POST)
    fun updateEvaluatePost(@Query("evaluateIdx") index: Int): Call<MyEvaluationShort>

    // 내가 쓴 글 (시험 정보)
    @GET(EXAM_POSTS)
    fun getExamPosts(@Query("page") page: Int): Call<ExamPost>

    @GET(UPDATE_EXAM_POSTS)
    fun updateExamPost(@Query("examIdx") id: Int): Call<ExamPostShort>

    // 검색결과 페이지 호출 API
    @GET(LECTURE)
    fun getSearchResult(@Query("query") searchTerm: String): Call<JsonElement>

    // 검색결과 자세히 보기(강의평)
    @GET(LECTURE)
    fun getSearchResultDetail(
        @Query("subjtNmname") subjectName: String,
        @Query("reprPrfsEnoNm") professorName: String
    ): Call<JsonElement>

    // 시험 정보 보기 API
    @GET(EXAM)
    fun getExamInfo(
        @Query("subjtNmname") subjectName: String,
        @Query("reprPrfsEnoNm") professorName: String
    ): Call<JsonElement>

    // TODO 나머지 API도 추가

    companion object {
        fun create(): IRetrofit {

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder().authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    when (response.code) {
                        400 -> {
                            // TODO 로그인 에러
                            return response.request
                        }
                        401 -> {
                            val refreshToken = TimeTableSelPref.prefs.getRefreshToken()
                            return refreshToken?.let {
                                response.request.newBuilder().header("Authorization",
                                    it
                                ).build()
                            }
                        }
                        else -> {
                            return response.request
                        }
                    }
                }
            }).addInterceptor(logger)

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IRetrofit::class.java)
        }
    }
}