package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.dataclass.*
import com.kunize.uswtimetable.ui.login.User
import com.kunize.uswtimetable.util.API.BASE_URL
import com.kunize.uswtimetable.util.API.EVALUATE_POST
import com.kunize.uswtimetable.util.API.EXAM
import com.kunize.uswtimetable.util.API.EXAM_POSTS
import com.kunize.uswtimetable.util.API.LECTURE_DETAIL_EVALUATION
import com.kunize.uswtimetable.util.API.LECTURE_DETAIL_EXAM
import com.kunize.uswtimetable.util.API.LECTURE_DETAIL_INFO
import com.kunize.uswtimetable.util.API.SEARCH
import com.kunize.uswtimetable.util.API.LECTURE_MAIN
import com.kunize.uswtimetable.util.API.LOGIN
import com.kunize.uswtimetable.util.API.MY_PAGE
import com.kunize.uswtimetable.util.API.NOTICE
import com.kunize.uswtimetable.util.API.NOTICE_LIST
import com.kunize.uswtimetable.util.API.PASSWORD
import com.kunize.uswtimetable.util.API.PASSWORD_RESET
import com.kunize.uswtimetable.util.API.QUIT
import com.kunize.uswtimetable.util.API.REQUEST_REFRESH
import com.kunize.uswtimetable.util.API.SIGN_UP
import com.kunize.uswtimetable.util.API.SIGN_UP_EMAIL_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_ID_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_SCHOOL_CHECK
import com.kunize.uswtimetable.util.API.UPDATE_EVALUATE_POST
import com.kunize.uswtimetable.util.API.UPDATE_EXAM_POSTS
import com.kunize.uswtimetable.util.API.WRITE_LECTURE_EVALUATION
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.isJsonArray
import com.kunize.uswtimetable.util.isJsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface IRetrofit {

    // Refresh Token
    @POST(REQUEST_REFRESH)
    fun requestRefresh(@Header("RefreshToken")refresh: String, @Body refreshToken: Token): Call<Token>

    // 메인 페이지 요청 API
    @GET()
    fun getMain(): Call<JsonElement>

    // 회원가입 요청 API
    @POST(SIGN_UP)
    suspend fun signUp(@Body info: SignUpFormat): Response<SuccessCheckDto>

    // 아이디 중복 확인 요청 API
    @POST(SIGN_UP_ID_CHECK)
    suspend fun checkId(@Body loginId: CheckIdFormat): Response<OverlapCheckDto>

    // 이메일 중복 확인 요청 API
    @POST(SIGN_UP_EMAIL_CHECK)
    suspend fun checkEmail(@Body email: CheckEmailFormat): Response<OverlapCheckDto>

    // 학교 메일 인증 API
    @GET(SIGN_UP_SCHOOL_CHECK)
    fun verifyEmail()

    // 공지사항 리스트 API
    @GET(NOTICE_LIST)
    suspend fun getNoticeList(): Response<NoticeListDto>

    // 공지사항 API
    @GET(NOTICE)
    suspend fun getNotice(@Query("notice_id") id: Long): Response<NoticeDetailDto>

    // 비밀번호 찾기(임시 비밀번호 전송) API
    @POST(PASSWORD)
    fun findPassword(@Body info: UserIdEmail): Call<SuccessCheckDto>

    // 비밀번호 재설정 API
    @POST(PASSWORD_RESET)
    fun resetPassword(@Body password: String): Call<SuccessCheckDto>

    // 로그인 요청 API
    @POST(LOGIN)
    suspend fun login(@Body info: LoginIdPassword): Response<Token>

    // 회원탈퇴 요청 API
    @POST(QUIT)
    fun quit(@Body info: LoginIdPassword): Call<SuccessCheckDto>

    // 내 정보 페이지 호출 API
    @GET(MY_PAGE)
    suspend fun getUserData(): Response<UserDataDto>

    // 내가 쓴 글 (강의평가)
    @GET(EVALUATE_POST)
    suspend fun getEvaluatePosts(@Query("page") page: Int): Response<MyEvaluationListDto>

    // 내가 쓴 글 (강의평가 수정)
    @GET(UPDATE_EVALUATE_POST)
    suspend fun updateEvaluatePost(@Body info: MyEvaluationEditDto)

    // 내가 쓴 글 (시험 정보)
    @GET(EXAM_POSTS)
    suspend fun getExamPosts(): Response<MyExamInfoListDto>

    // 내가 쓴 글 (시험 정보 수정)
    @GET(UPDATE_EXAM_POSTS)
    suspend fun updateExamPost(@Body info: MyExamInfoEditDto)

    // 검색결과 자세히 보기 (LECTURE)
    @GET(LECTURE_DETAIL_INFO)
    suspend fun getLectureDetailInfo(@Query("lectureId") lectureId: Long): Response<LectureDetailInfoDto>

    // 검색결과 자세히 보기 (Evaluation)
    @GET(LECTURE_DETAIL_EVALUATION)
    suspend fun getLectureDetailEvaluation(
        @Query("lectureId") lectureId: Long,
        @Query("page") page: Int
    ): Response<LectureDetailEvaluationDto>

    // 검색결과 자세히 보기 (Exam)
    @GET(LECTURE_DETAIL_EXAM)
    suspend fun getLectureDetailExam(
        @Query("lectureId") lectureId: Long,
        @Query("page") page: Int
    ): Response<LectureDetailExamDto>

    // 검색결과 페이지 API(강의평)
    @GET(SEARCH)
    suspend fun getSearchResultDetail(
        @Query("searchValue") searchValue: String,
        @Query("option") option: String,
        @Query("page") page: Int
    ): Response<LectureMainDto>

    // 시험 정보 보기 API
    @GET(EXAM)
    fun getExamInfo(
        @Query("subjtNmname") subjectName: String,
        @Query("reprPrfsEnoNm") professorName: String
    ): Call<JsonElement>

    // 메인 페이지
    @GET(LECTURE_MAIN)
    suspend fun getLectureMainList(
        @Query("option") option: String,
        @Query("page") page: Int = 1
    ): Response<LectureMainDto>

    @POST(WRITE_LECTURE_EVALUATION)
    suspend fun postLectureEvaluation(
        @Query("lectureId") lectureId: Long,
        @Body info: LectureEvaluationPostDto
    ): Response<String>

    companion object {
        private var retrofitService: IRetrofit? = null
        private var retrofitServiceWithNoToken: IRetrofit? = null

        fun getInstance(): IRetrofit {
            if (retrofitService == null) {
                val client = getClient()
                retrofitService = client.create(IRetrofit::class.java)
            }
            return retrofitService!!
        }

        fun getInstanceWithNoToken(): IRetrofit {
            if (retrofitServiceWithNoToken == null) {
                val client = getClientWithNoToken()
                retrofitServiceWithNoToken = client.create(IRetrofit::class.java)
            }
            return retrofitServiceWithNoToken!!
        }

        private fun getClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(TokenAuthenticator()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getClientWithNoToken(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(null))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getOkHttpClient(
            authenticator: TokenAuthenticator?
        ): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONArray(message).toString(4))
                    else ->
                        Log.d(TAG, "CONNECTION INFO -> $message")
                }
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            authenticator?.apply {
                client
                    .addInterceptor(AuthenticationInterceptor())
                    .authenticator(this)
            }

            return client.build()
        }
    }
}

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = TimeTableSelPref.encryptedPrefs.getAccessToken() ?: ""
        val request = chain.request().newBuilder()
            .addHeader("AccessToken", accessToken).build()
        Log.d(TAG, "AuthenticationInterceptor - intercept() called / request header: ${request.headers}")
        return chain.proceed(request)
    }
}

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        Log.d(TAG, "TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청")

        val access = TimeTableSelPref.encryptedPrefs.getAccessToken() ?: ""
        val refresh = TimeTableSelPref.encryptedPrefs.getRefreshToken() ?: ""
        val tokenResponse = IRetrofit.getInstanceWithNoToken().requestRefresh(refresh=refresh, Token(accessToken = access, refreshToken = refresh)).execute()

        return if (handleResponse(tokenResponse)) {
            Log.d(TAG, "TokenAuthenticator - authenticate() called / 중단된 API 재요청")
            response.request
                .newBuilder()
                .removeHeader("AccessToken")
                .header("AccessToken", TimeTableSelPref.encryptedPrefs.getAccessToken() ?: "")
                .build()
        } else {
            null
        }
    }

    private fun handleResponse(tokenResponse: Response<Token>) =
        if (tokenResponse.isSuccessful && tokenResponse.body() != null) {
            TimeTableSelPref.encryptedPrefs.saveAccessToken(tokenResponse.body()!!.accessToken)
            TimeTableSelPref.encryptedPrefs.saveRefreshToken(tokenResponse.body()!!.refreshToken)
            true
        } else {
            User.logout()
            false
        }
}