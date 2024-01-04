package com.suwiki.feature.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
) : ContainerHost<SignupState, SignupSideEffect>, ViewModel() {
  override val container: Container<SignupState, SignupSideEffect> = container(SignupState())
}
