package com.suwiki.core.common

import javax.inject.Qualifier

@Qualifier
annotation class Dispatcher(val suwikiDispatcher: SuwikiDispatchers)

enum class SuwikiDispatchers {
  IO,
}
