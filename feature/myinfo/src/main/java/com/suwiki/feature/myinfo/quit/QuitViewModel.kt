package com.suwiki.feature.myinfo.quit

import androidx.lifecycle.ViewModel
import com.suwiki.domain.user.usecase.QuitUseCase
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
class QuitViewModel @Inject constructor(
  private val quitUseCase: QuitUseCase,
) : ContainerHost<QuitState, QuitSideEffect>, ViewModel() {
  override val container: Container<QuitState, QuitSideEffect> = container(QuitState())

  @OptIn(OrbitExperimental::class)
  fun updateId(id: String) = blockingIntent {
    reduce { state.copy(id = id) }
  }

  @OptIn(OrbitExperimental::class)
  fun updatePassword(password: String) = blockingIntent {
    reduce { state.copy(password = password) }
  }

  @OptIn(OrbitExperimental::class)
  fun quit(id: String, password: String) = blockingIntent {
    showLoadingScreen()
    quitUseCase(
      id = id,
      password = password,
    ).onSuccess {
      popBackStack()
    }.onFailure {
      postSideEffect(QuitSideEffect.HandleException(it))
    }
    hideLoadingScreen()
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }

  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun toggleShowPassword() = intent { reduce { state.copy(showPassword = !state.showPassword) } }
  fun popBackStack() = intent { postSideEffect(QuitSideEffect.PopBackStack) }
}
