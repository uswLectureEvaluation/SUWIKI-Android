package com.suwiki.remote

import com.suwiki.model.Result
import com.suwiki.model.SuwikiError

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>

    sealed interface Failure : ApiResult<Nothing> {
        data class HttpError(val code: Int, val message: String, val body: String) : Failure
        data class NetworkError(val throwable: Throwable) : Failure
        data class UnknownError(val throwable: Throwable) : Failure
        data class CustomError(val code: Int, val error: SuwikiError) : Failure

        fun safeThrowable(): Throwable = when (this) {
            is HttpError -> IllegalStateException("$message $body")
            is NetworkError -> throwable
            is UnknownError -> throwable
            is CustomError -> Exception("$code: $error")
        }
    }

    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure
    fun getOrThrow(): T {
        throwFailure()
        return (this as Success).data
    }

    fun getOrNull(): T? =
        when (this) {
            is Success -> data
            else -> null
        }

    fun failureOrThrow(): Failure {
        throwOnSuccess()
        return this as Failure
    }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> safeThrowable()
            else -> null
        }

    companion object {
        fun <R> successOf(result: R): ApiResult<R> = Success(result)
    }
}

inline fun <T> ApiResult<T>.onSuccess(
    action: (value: T) -> Unit,
): ApiResult<T> {
    if (isSuccess()) action(getOrThrow())
    return this
}

inline fun <T> ApiResult<T>.onFailure(
    action: (error: ApiResult.Failure) -> Unit,
): ApiResult<T> {
    if (isFailure()) action(failureOrThrow())
    return this
}

internal fun ApiResult<*>.throwOnSuccess() {
    if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

internal fun ApiResult<*>.throwFailure() {
    if (this is ApiResult.Failure) throw safeThrowable()
}

fun ApiResult.Failure.toSuwikiError(): SuwikiError {
    return when (this) {
        is ApiResult.Failure.CustomError -> error
        is ApiResult.Failure.HttpError -> SuwikiError.HttpError(code, message, body)
        is ApiResult.Failure.NetworkError -> SuwikiError.NetworkError
        is ApiResult.Failure.UnknownError -> SuwikiError.CustomError(-1, throwable.message ?: "")
    }
}

fun <T> ApiResult<T>.toResult(): Result<T> {
    return if (this.isSuccess()) {
        Result.Success(getOrThrow())
    } else {
        Result.Failure(failureOrThrow().toSuwikiError())
    }
}
