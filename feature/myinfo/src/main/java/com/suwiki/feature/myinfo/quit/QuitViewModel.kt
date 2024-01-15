package com.suwiki.feature.myinfo.quit

import androidx.lifecycle.ViewModel
import com.suwiki.domain.user.usecase.QuitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
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

  fun updateId(id: String) = intent {
    reduce { state.copy(id = id) }
  }

  fun updatePassword(password: String) = intent {
    reduce { state.copy(password = password) }
  }

  fun quit(id: String, password: String) = intent {
    quitUseCase(
      id = id,
      password = password
    ).onSuccess {
      popBackStack()
    }.onFailure {
      postSideEffect(QuitSideEffect.HandleException(it))
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }

  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun toggleShowPassword() = intent { reduce { state.copy(showPassword = !state.showPassword) } }
  fun popBackStack() = intent { postSideEffect(QuitSideEffect.PopBackStack) }
}