package com.kunize.uswtimetable.ui.signup

import android.util.Log
import com.kunize.uswtimetable.dataclass.EmailCheckDto
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState
import com.kunize.uswtimetable.util.Result

class CertificateEmail {
    private val retrofitManager = RetrofitManager.instance
    private var result: Result<EmailCheckDto>? = null

    fun certificate(email: String): Result<EmailCheckDto> {
        val fullEmail = "${email}@$SCHOOL_DOMAIN"

        // TODO 인증 로직 (서버와 통신)
        retrofitManager.emailCheck(fullEmail, completion = { state, data ->
            result = when(state) {
                ResponseState.OK -> {
                    if(data == null) Result.Error(NullPointerException("실패: 받아온 인증 데이터 없음"))
                    else Result.Success(data)
                }
                ResponseState.FAIL -> {
                    Result.Error(Exception("실패: 인증 통신 실패"))
                }
            }
        })
        Log.d(TAG, "CertificateEmail - certificate() called / $result")
        return result ?: Result.Error(Exception("실패: 초기화되지 않음"))
    }
}