package com.suwiki.common.android

import javax.inject.Qualifier

@Qualifier
annotation class Dispatcher(val suwikiDispatcher: SuwikiDispatchers)

enum class SuwikiDispatchers {
  IO,
}
