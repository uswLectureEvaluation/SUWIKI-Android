package com.kunize.uswtimetable.util.extensions

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener { trySend(Unit) }
    awaitClose { setOnClickListener(null) }
}

fun View.setOnThrottleClick(
    scope: CoroutineScope,
    duration: Long = 300L,
    onClick: () -> Unit,
) {
    clicks()
        .throttleFirst(duration)
        .onEach { onClick() }
        .launchIn(scope)
}
