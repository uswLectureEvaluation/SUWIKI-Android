package com.suwiki.feature.SignupComplete.complete

import androidx.lifecycle.ViewModel
import com.suwiki.core.ui.util.REGEX
import com.suwiki.feature.signup.complete.SignupCompleteSideEffect
import com.suwiki.feature.signup.complete.SignupCompleteState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignupCompleteViewModel @Inject constructor(
) : ContainerHost<SignupCompleteState, SignupCompleteSideEffect>, ViewModel() {
  override val container: Container<SignupCompleteState, SignupCompleteSideEffect> = container(SignupCompleteState.Default)

  fun navigateLogin() = intent { postSideEffect(SignupCompleteSideEffect.NavigateLogin) }
  fun openSchoolWebsite() = intent { postSideEffect(SignupCompleteSideEffect.OpenSchoolWebSite) }
}
