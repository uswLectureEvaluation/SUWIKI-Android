package com.suwiki.presentation.SignupComplete.complete

import androidx.lifecycle.ViewModel
import com.suwiki.presentation.signup.complete.SignupCompleteSideEffect
import com.suwiki.presentation.signup.complete.SignupCompleteState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignupCompleteViewModel @Inject constructor() : ContainerHost<SignupCompleteState, SignupCompleteSideEffect>, ViewModel() {

  override val container: Container<SignupCompleteState, SignupCompleteSideEffect> = container(
    SignupCompleteState.Default,
  )

  fun navigateLogin() = intent { postSideEffect(SignupCompleteSideEffect.NavigateLogin) }
  fun openSchoolWebsite() = intent { postSideEffect(SignupCompleteSideEffect.OpenSchoolWebSite) }
}
