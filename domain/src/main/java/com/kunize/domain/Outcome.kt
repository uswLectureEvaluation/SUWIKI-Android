package com.kunize.domain

sealed class Outcome<T> {
    object Loading : Outcome<Nothing>()
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure<T>(val error: Throwable?) : Outcome<T>()
}