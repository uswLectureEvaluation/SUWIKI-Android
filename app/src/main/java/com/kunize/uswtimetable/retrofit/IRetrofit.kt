package com.kunize.uswtimetable.retrofit

import android.util.Log
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
import com.kunize.uswtimetable.util.API.REQUEST_REFRESH
import com.kunize.uswtimetable.util.API.SIGN_UP
import com.kunize.uswtimetable.util.API.SIGN_UP_EMAIL_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_ID_CHECK
import com.kunize.uswtimetable.util.API.SIGN_UP_SCHOOL_CHECK
import com.kunize.uswtimetable.util.API.UPDATE_EVALUATE_POST
import com.kunize.uswtimetable.util.API.UPDATE_EXAM_POSTS
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
import retrofit2.http.*

interface IRetrofit {

    // Refresh Token
    @FormUrlEncoded
    @POST(REQUEST_REFRESH)
    fun requestRefresh(@FieldMap tokens: HashMap<String, String>): Response<Token>

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
    suspend fun getNoticeList(): Response<List<NoticeDto>>

    // 공지사항 API
    @GET(NOTICE)
    suspend fun getNotice(@Query("notice_id") id: Long): Call<NoticeDetailDto>

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

    private val accessToken = TimeTableSelPref.encryptedPrefs.getAccessToken() ?: ""

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .addHeader("AccessToken", accessToken).build()
        Log.d(TAG, "AuthenticationInterceptor - intercept() called / access: $accessToken")
        return chain.proceed(request)
    }
}

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request {
        val updatedToken = getUpdatedToken() ?: ""
        return response.request.newBuilder().header("AccessToken", updatedToken).build()
    }

    private fun getUpdatedToken(): String? {
        val requestParams = HashMap<String, String>()
        val access = TimeTableSelPref.encryptedPrefs.getAccessToken() ?: ""
        val refresh = TimeTableSelPref.encryptedPrefs.getRefreshToken() ?: ""
        requestParams[access] = refresh

        val authTokenResponse = IRetrofit.getInstanceWithNoToken().requestRefresh(requestParams)
        if (authTokenResponse.isSuccessful) {
            authTokenResponse.body()?.let { tokens ->
                tokens.accessToken.let { TimeTableSelPref.encryptedPrefs.saveAccessToken(it) }
                tokens.refreshToken.let { TimeTableSelPref.encryptedPrefs.saveRefreshToken(it) }
            }
        } else {
            Log.d(
                TAG,
                "TokenAuthenticator - getUpdatedToken() called failed / ${authTokenResponse.code()}: ${authTokenResponse.message()}"
            )
        }

        return authTokenResponse.body()?.accessToken
    }
}