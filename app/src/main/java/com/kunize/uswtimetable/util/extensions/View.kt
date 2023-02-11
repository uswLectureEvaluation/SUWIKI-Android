package com.kunize.uswtimetable.util.extensions

import android.view.View
import com.kunize.uswtimetable.ui.common.OnThrottleClickListener
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

fun View.onThrottleClick(
    scope: CoroutineScope,
    duration: Long = 300L,
    onClick: (v: View) -> Unit,
) {
    clicks()
        .throttleFirst(duration)
        .onEach { onClick(this) }
        .launchIn(scope)
}

fun View.onThrottleClick(
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun View.onThrottleClick(
    interval: Long,
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener, interval))
}
