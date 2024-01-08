package com.suwiki.feature.login.findid

import androidx.lifecycle.ViewModel
import com.suwiki.core.ui.util.REGEX
import com.suwiki.domain.login.usecase.FindIdUseCase
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
class FindIdViewModel @Inject constructor(
  private val findIdUseCase: FindIdUseCase,
) : ContainerHost<FindIdState, FindIdSideEffect>, ViewModel() {
  override val container: Container<FindIdState, FindIdSideEffect> = container(FindIdState())

  @OptIn(OrbitExperimental::class)
  fun updateEmail(email: String) = blockingIntent {
    reduce {
      state.copy(
        email = email,
        showFindIdButton = false,
        showEmailNoticeHelperText = true,
        showEmailInvalidHelperText = false,
      )
    }
  }

  fun checkEmailInvalid(email: String) {
    when {
      email.isEmpty() -> Unit
      email.matches(REGEX.EMAIL) -> showFindIdButton()
      else -> showEmailInvalidHelperText()
    }
  }

  private fun showFindIdButton() = intent {
    reduce {
      state.copy(
        showFindIdButton = true,
        showEmailNoticeHelperText = false,
        showEmailInvalidHelperText = false,
      )
    }
  }

  private fun showEmailInvalidHelperText() = intent {
    reduce {
      state.copy(
        showFindIdButton = false,
        showEmailNoticeHelperText = false,
        showEmailInvalidHelperText = true,
      )
    }
  }

  fun popBackStack() = intent { postSideEffect(FindIdSideEffect.PopBackStack) }
  fun findId() = intent {
    reduce { state.copy(isLoading = true) }
    findIdUseCase(email = state.email)
      .onSuccess {
        reduce { state.copy(showFindIdSuccessDialog = true) }
      }
      .onFailure {
        postSideEffect(FindIdSideEffect.HandleException(it))
      }
    reduce { state.copy(isLoading = false) }
  }

  fun hideSuccessDialog() = intent { reduce { state.copy(showFindIdSuccessDialog = false) } }
}
