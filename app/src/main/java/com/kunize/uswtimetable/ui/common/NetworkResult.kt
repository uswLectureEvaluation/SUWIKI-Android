package com.kunize.uswtimetable.ui.common

data class NetworkResult<T>(
    val status: NetworkStatus,
    val code: Int,
    val message: String?,
    val data: T
)

enum class NetworkStatus {
    SUCCESS,
    FAIL,
    EMPTY
}
