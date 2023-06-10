package com.kunize.uswtimetable.domain.model

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val error: SuwikiError) : Result<Nothing>()

    val isSuccessful: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    fun getOrNull() = (this as? Success)?.data
    fun errorOrNull() = (this as? Failure)?.error
    fun errorOrThrow() =
        if (isFailure) (this as Failure).error else throw IllegalStateException("Cannot be called under Success conditions.")
}
