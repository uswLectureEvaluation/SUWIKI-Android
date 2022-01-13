package com.kunize.uswtimetable.signup

import com.kunize.uswtimetable.dataclass.EmailCheckDto
import com.kunize.uswtimetable.dataclass.Result
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN
import com.kunize.uswtimetable.util.ResponseState

class CertificateEmail {
    private val retrofitManager = RetrofitManager.instance
    private lateinit var result: Result<EmailCheckDto>

    fun certificate(email: String): Result<EmailCheckDto> {
        val fullEmail = "${email}@$SCHOOL_DOMAIN"

        // TODO 인증 로직 (서버와 통신)
        retrofitManager.emailCheck(fullEmail, completion = { state, data ->
            result = when(state) {
                ResponseState.OK -> {
                    if(data == null) Result.Fail("실패: 받아온 인증 데이터 없음")
                    else Result.Success(data)
                }
                ResponseState.FAIL -> {
                    Result.Fail("실패: 인증 통신 실패")
                }
            }
        })
        return result
    }
}