package com.suwiki.presentation.common.ui.util

/**
 * 클릭이 지연될 시간을 정의하는 변수
 */
private const val CLICK_EVENT_DELAY_TIME: Long = 300L

internal interface MultipleEventsCutter {
  fun processEvent(event: () -> Unit)

  companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
  MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
  private val now: Long
    get() = System.currentTimeMillis()

  private var lastEventTimeMs: Long = 0

  override fun processEvent(event: () -> Unit) {
    if (now - lastEventTimeMs >= CLICK_EVENT_DELAY_TIME) {
      event.invoke()
    }
    lastEventTimeMs = now
  }
}
