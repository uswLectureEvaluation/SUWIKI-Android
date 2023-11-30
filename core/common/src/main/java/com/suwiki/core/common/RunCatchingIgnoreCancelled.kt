package com.suwiki.core.common

import kotlin.coroutines.cancellation.CancellationException

inline fun <T> runCatchingIgnoreCancelled(block: () -> T): Result<T> = runCatching(block)
  .onFailure { error ->
    if (error is CancellationException) throw error
  }
