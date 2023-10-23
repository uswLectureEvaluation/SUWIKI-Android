package com.suwiki.core.network.retrofit

import com.suwiki.core.model.exception.ForbiddenException
import com.suwiki.core.model.exception.NetworkException
import com.suwiki.core.model.exception.NotFoundException
import com.suwiki.core.model.exception.RequestFailException
import com.suwiki.core.model.exception.SuwikiServerError
import com.suwiki.core.model.exception.UnknownException

sealed interface ApiResult<out T> {
  data class Success<T>(val data: T) : ApiResult<T>

  sealed interface Failure : ApiResult<Nothing> {
    data class HttpError(val code: Int, val message: String, val body: String) : Failure
    data class NetworkError(val throwable: Throwable) : Failure
    data class UnknownApiError(val throwable: Throwable) : Failure

    fun safeThrowable(): Throwable =
      when (this) {
        is HttpError -> handleHttpError(this)
        is NetworkError -> throwable
        is UnknownApiError -> throwable
      }
  }

  val isSuccess: Boolean
    get() = this is Success

  val isFailure: Boolean
    get() = this is Failure

  fun getOrThrow(customHttpErrorHandler: (Failure.HttpError.() -> Exception)? = null): T {
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
  if (isSuccess) action(getOrThrow())
  return this
}

inline fun <T> ApiResult<T>.onFailure(
  action: (error: ApiResult.Failure) -> Unit,
): ApiResult<T> {
  if (isFailure) action(failureOrThrow())
  return this
}

internal fun ApiResult<*>.throwOnSuccess() {
  if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

internal fun ApiResult<*>.throwFailure() {
  if (this is ApiResult.Failure) {
    throw safeThrowable()
  }
}

private fun handleHttpError(httpError: ApiResult.Failure.HttpError): Exception {
  return getSuwikiErrorBody(httpError.body).getOrNull()?.run {
    handleSuwikiError(this)
  } ?: handleNonSuwikiError(httpError.code)
}

private fun handleSuwikiError(suwikiErrorResponse: SuwikiErrorResponse): Exception = runCatching {
  SuwikiServerError.valueOf(suwikiErrorResponse.suwikiCode).exception
}.getOrNull() ?: handleNonSuwikiError(suwikiErrorResponse.httpStatusCode)

private fun handleNonSuwikiError(httpStatusCode: Int) = when (httpStatusCode) {
  400 -> RequestFailException()
  403 -> ForbiddenException()
  404 -> NotFoundException()
  500, 501, 502, 503, 504, 505 -> NetworkException()
  else -> UnknownException()
}

private fun getSuwikiErrorBody(body: String) = runCatching {
  KotlinSerializationUtil.json.decodeFromString<SuwikiErrorResponse>(body)
}
