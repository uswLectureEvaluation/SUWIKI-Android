package com.suwiki.feature.signup.complete

sealed interface SignupCompleteState {
  data object Default : SignupCompleteState
}

sealed interface SignupCompleteSideEffect {
  data object OpenSchoolWebSite : SignupCompleteSideEffect
  data object NavigateLogin : SignupCompleteSideEffect
}
