package com.kunize.uswtimetable.util.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > duration) {
            lastEmissionTime = currentTime
            emit(it)
        }
    }
}
