package com.kunize.uswtimetable.util

import com.kunize.uswtimetable.util.Constants.TOKEN

object Constants {
    const val TAG = "로그"
    const val TOKEN = "token"
    const val ID_COUNT_LIMIT = 20
    const val PW_COUNT_LIMIT = 24
}

enum class PasswordAgain {
    SAME,
    DIFFERENT,
    EMPTY
}

object API {
    const val BASE_URL: String = "https://61dc5273591c3a0017e1a86b.mockapi.io"
    const val NOTICE: String = "/notices"
    const val LECTURE: String = "/lecture"
    const val EXAM: String = "/lecture/exam"
    const val PASSWORD: String = "/pwinquiry"
    const val MY_PAGE: String = "/{$TOKEN}/mypage"
    const val SIGN_UP: String = "/signup"
    const val SIGN_UP_ID_CHECK: String = "/signup_id_check"
    const val SIGN_UP_EMAIL_CHECK: String = "/signup_email_check"
    const val SIGN_UP_SCHOOL_CHECK: String = "/signup_check"
}

enum class ResponseState {
    OK, FAIL
}