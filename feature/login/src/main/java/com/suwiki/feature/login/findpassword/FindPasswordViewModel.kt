package com.suwiki.feature.login.findpassword

import androidx.lifecycle.ViewModel
import com.suwiki.domain.login.usecase.FindPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
  private val findPasswordUseCase: FindPasswordUseCase,
) : ContainerHost<FindPasswordState, FindPasswordSideEffect>, ViewModel() {
  override val container: Container<FindPasswordState, FindPasswordSideEffect> = container(FindPasswordState())

  @OptIn(OrbitExperimental::class)
  fun updateId(id: String) = blockingIntent { reduce { state.copy(id = id) } }

  @OptIn(OrbitExperimental::class)
  fun updateEmail(email: String) = blockingIntent { reduce { state.copy(email = email) } }
}
