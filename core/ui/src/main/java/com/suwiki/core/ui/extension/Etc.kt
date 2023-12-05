package com.suwiki.core.ui.extension

internal inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
  run()
} else {
  this
}
