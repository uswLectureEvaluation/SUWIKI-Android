package com.suwiki.feature.login.resetpassword

import androidx.lifecycle.ViewModel
import com.suwiki.core.ui.util.REGEX
import com.suwiki.domain.user.usecase.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
  val resetPasswordUseCase: ResetPasswordUseCase,
) : ContainerHost<ResetPasswordState, ResetPasswordSideEffect>, ViewModel() {
  override val container: Container<ResetPasswordState, ResetPasswordSideEffect> = container(ResetPasswordState())

  fun checkNewPasswordInvalid(password: String) = intent {
    reduce { state.copy(showPasswordInvalidErrorText = !(password == "" || password.matches(REGEX.PASSWORD))) }
  }

  fun checkNewPasswordMatch(password: String) = intent {
    reduce { state.copy(showPasswordNotMatchErrorText = !(password == "" || password == state.newPassword)) }
  }

  fun checkShowResetPasswordButton() = intent {
    Timber.d("state.showPasswordInvalidErrorText: ${state.showPasswordInvalidErrorText}")
    Timber.d("state.showPasswordNotMatchErrorText: ${state.showPasswordNotMatchErrorText}")
    reduce {
      state.copy(
        showResetPasswordButton =
        (!state.showPasswordInvalidErrorText && !state.showPasswordNotMatchErrorText) &&
          (state.currentPassword != "" && state.newPassword != "" && state.checkNewPassword != "")
      )
    }
  }

  fun resetPassword() = intent {
    showLoadingScreen()
    resetPasswordUseCase(
      currentPassword = state.currentPassword,
      newPassword = state.newPassword,
    ).onSuccess {
      showResetPasswordDialog()
    }.onFailure {
      postSideEffect(ResetPasswordSideEffect.HandleException(it))
    }
    hideLoadingScreen()
  }

  @OptIn(OrbitExperimental::class)
  fun updateCurrentPassword(password: String) = blockingIntent {
    reduce {
      state.copy(
        currentPassword = password,
        showResetPasswordButton = false,
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateNewPassword(password: String) = blockingIntent {
    reduce {
      state.copy(
        newPassword = password,
        showResetPasswordButton = false,
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateCheckNewPassword(password: String) = blockingIntent {
    reduce {
      state.copy(
        checkNewPassword = password,
        showResetPasswordButton = false,
      )
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  private fun showResetPasswordDialog() = intent { reduce { state.copy(showResetPasswordDialog = true) } }
  fun hideResetPasswordDialog() = intent { reduce { state.copy(showResetPasswordDialog = false) } }
  fun toggleShowCurrentPassword() = intent { reduce { state.copy(showCurrentPassword = !state.showCurrentPassword) } }
  fun toggleShowNewPassword() = intent { reduce { state.copy(showNewPassword = !state.showNewPassword) } }
  fun toggleShowCheckNewPassword() = intent { reduce { state.copy(showCheckNewPassword = !state.showCheckNewPassword) } }
  fun popBackStack() = intent { postSideEffect(ResetPasswordSideEffect.PopBackStack) }
  fun navigateFindPassword() = intent { postSideEffect(ResetPasswordSideEffect.NavigateFindPassword) }
  fun navigateLogin() = intent { postSideEffect(ResetPasswordSideEffect.NavigateLogin) }
}
