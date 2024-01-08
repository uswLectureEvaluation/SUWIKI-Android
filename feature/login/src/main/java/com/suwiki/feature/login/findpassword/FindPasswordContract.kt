package com.suwiki.feature.login.findpassword

data class FindPasswordState(
  val email: String = "",
  val id: String = "",
  val isLoading: Boolean = false,
)

sealed interface FindPasswordSideEffect {
  data class HandleException(val throwable: Throwable) : FindPasswordSideEffect
  data object PopBackStack : FindPasswordSideEffect
}
