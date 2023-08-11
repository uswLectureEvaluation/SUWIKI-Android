package com.suwiki.model

sealed interface SuwikiError {
    object RequestFailure : SuwikiError // 요청 실패
    object TokenExpired : SuwikiError // JWT 만료
    object RestrictApproach : SuwikiError // 제한된 유저의 접근
    object NoResult : SuwikiError // 결과 값이 없는 경우
    data class HttpError(val code: Int, val message: String, val body: String) : SuwikiError
    object NetworkError : SuwikiError
    data class CustomError(val code: Int, val message: String) : SuwikiError

    companion object {
        fun fromCode(code: Int): SuwikiError? {
            return when (code) {
                400 -> RequestFailure
                401 -> TokenExpired
                403 -> RestrictApproach
                404 -> NoResult
                else -> null
            }
        }
    }
}
