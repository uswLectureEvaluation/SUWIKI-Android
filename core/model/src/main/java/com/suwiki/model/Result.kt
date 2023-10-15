package com.suwiki.model

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val error: SuwikiError) : Result<Nothing>()

    val isSuccessful: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    fun getOrNull() = (this as? Success)?.data
    fun errorOrNull() = (this as? Failure)?.error

    fun getOrThrow() =
        if (isSuccessful) (this as Success).data else throw IllegalStateException("Cannot be called under Failure conditions.")

    fun errorOrThrow() =
        if (isFailure) (this as Failure).error else throw IllegalStateException("Cannot be called under Success conditions.")

    fun <R> mapOnSuccess(transform: (T) -> R) = getOrThrow().run {
        return@run Success(transform(this))
    }

    fun <R> map(transform: (T) -> R): Result<R> {
        return if (isSuccessful) Success(transform(getOrThrow())) else (this as Failure)
    }
}

fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (isSuccessful) action(this.getOrThrow())
    return this
}

fun <T> Result<T>.onFailure(action: (SuwikiError) -> Unit): Result<T> {
    if (isFailure) action(this.errorOrThrow())
    return this
}