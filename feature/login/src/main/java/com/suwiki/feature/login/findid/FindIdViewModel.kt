package com.suwiki.feature.login.findid

import androidx.lifecycle.ViewModel
import com.suwiki.domain.login.usecase.FindIdUseCase
import com.suwiki.feature.login.findid.FindIdSideEffect
import com.suwiki.feature.login.findid.FindIdState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
  private val findIdUseCase: FindIdUseCase,
) : ContainerHost<FindIdState, FindIdSideEffect>, ViewModel() {
  override val container: Container<FindIdState, FindIdSideEffect> = container(FindIdState())

  @OptIn(OrbitExperimental::class)
  fun updateEmail(email: String) = blockingIntent { reduce { state.copy(email = email) } }
}
