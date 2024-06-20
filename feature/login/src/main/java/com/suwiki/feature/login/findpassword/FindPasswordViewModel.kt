package com.suwiki.feature.login.findpassword

import androidx.lifecycle.ViewModel
import com.suwiki.feature.common.ui.util.REGEX
import com.suwiki.domain.login.usecase.FindPasswordUseCase
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
class FindPasswordViewModel @Inject constructor(
  private val findPasswordUseCase: FindPasswordUseCase,
) : ContainerHost<FindPasswordState, FindPasswordSideEffect>, ViewModel() {
  override val container: Container<FindPasswordState, FindPasswordSideEffect> = container(FindPasswordState())

  private var isEmailValid: Boolean = false
  private var isIdValid: Boolean = false

  @OptIn(OrbitExperimental::class)
  fun updateId(id: String) = blockingIntent {
    reduce {
      isIdValid = false
      state.copy(
        id = id,
        showIdInvalidHelperText = false,
        showIdOverlapHelperText = false,
        showFindPasswordButton = false,
      )
    }
  }

  fun checkIdInvalid(id: String) {
    when {
      id.isEmpty() -> Unit
      id.matches(REGEX.ID) && isEmailValid -> showFindPasswordEmailButton()
      id.matches(REGEX.ID) -> hideIdInvalidHelperText()
      else -> showIdInvalidHelperText()
    }
  }

  private fun hideIdInvalidHelperText() = intent {
    reduce {
      isIdValid = true
      state.copy(
        showIdInvalidHelperText = false,
      )
    }
  }

  private fun showIdInvalidHelperText() = intent {
    reduce {
      state.copy(
        showIdInvalidHelperText = true,
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateEmail(email: String) = blockingIntent {
    reduce {
      state.copy(
        email = email,
        showFindPasswordButton = false,
        showEmailNoticeHelperText = true,
        showEmailInvalidHelperText = false,
      )
    }
  }

  fun checkEmailInvalid(email: String) {
    when {
      email.isEmpty() -> Unit
      email.matches(REGEX.EMAIL) && isIdValid -> showFindPasswordEmailButton()
      email.matches(REGEX.EMAIL) -> hideEmailInvalidHelperText()
      else -> showEmailInvalidHelperText()
    }
  }

  private fun showFindPasswordEmailButton() = intent {
    reduce {
      isIdValid = true
      isEmailValid = true
      state.copy(
        showFindPasswordButton = true,
        showEmailNoticeHelperText = false,
        showEmailInvalidHelperText = false,
      )
    }
  }

  private fun hideEmailInvalidHelperText() = intent {
    reduce {
      isEmailValid = true
      state.copy(
        showFindPasswordButton = false,
        showEmailNoticeHelperText = false,
        showEmailInvalidHelperText = false,
      )
    }
  }

  private fun showEmailInvalidHelperText() = intent {
    reduce {
      state.copy(
        showFindPasswordButton = false,
        showEmailNoticeHelperText = false,
        showEmailInvalidHelperText = true,
      )
    }
  }

  fun popBackStack() = intent { postSideEffect(FindPasswordSideEffect.PopBackStack) }
  fun findPassword() = intent {
    reduce { state.copy(isLoading = true) }
    findPasswordUseCase(loginId = state.id, email = state.email)
      .onSuccess {
        reduce { state.copy(showFindPasswordSuccessDialog = true) }
      }
      .onFailure {
        postSideEffect(FindPasswordSideEffect.HandleException(it))
      }
    reduce { state.copy(isLoading = false) }
  }

  fun hideSuccessDialog() = intent { reduce { state.copy(showFindPasswordSuccessDialog = false) } }
}
