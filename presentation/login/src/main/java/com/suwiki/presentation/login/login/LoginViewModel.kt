package com.suwiki.presentation.login.login

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.exception.EmailNotAuthedException
import com.suwiki.core.model.exception.LoginFailedException
import com.suwiki.core.model.exception.PasswordErrorException
import com.suwiki.domain.login.usecase.LoginUseCase
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
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
) : ContainerHost<LoginState, LoginSideEffect>, ViewModel() {
  override val container: Container<LoginState, LoginSideEffect> = container(LoginState())

  fun login() = intent {
    reduce { state.copy(isLoading = true) }
    loginUseCase(loginId = state.id, password = state.password)
      .onSuccess {
        postSideEffect(LoginSideEffect.PopBackStack)
      }
      .onFailure {
        when (it) {
          is LoginFailedException -> reduce { state.copy(showLoginFailDialog = true) }
          is PasswordErrorException -> reduce { state.copy(showLoginFailDialog = true) }
          is EmailNotAuthedException -> reduce { state.copy(showEmailNotAuthDialog = true) }
          else -> postSideEffect(LoginSideEffect.HandleException(it))
        }
      }

    reduce { state.copy(isLoading = false) }
  }

  fun navigateFindId() = intent { postSideEffect(LoginSideEffect.NavigateFindId) }
  fun navigateFindPassword() = intent { postSideEffect(LoginSideEffect.NavigateFindPassword) }
  fun navigateSignup() = intent { postSideEffect(LoginSideEffect.NavigateSignUp) }

  fun toggleShowPassword() = intent { reduce { state.copy(showPassword = !state.showPassword) } }

  fun hideLoginFailDialog() = intent { reduce { state.copy(showLoginFailDialog = false) } }

  fun hideEmailNotAuthDialog() = intent { reduce { state.copy(showEmailNotAuthDialog = false) } }

  @OptIn(OrbitExperimental::class)
  fun updateId(id: String) = blockingIntent { reduce { state.copy(id = id) } }

  @OptIn(OrbitExperimental::class)
  fun updatePassword(password: String) = blockingIntent { reduce { state.copy(password = password) } }

  fun showAgreementBottomSheet() = intent { reduce { state.copy(showAgreementBottomSheet = true) } }
  fun hideAgreementBottomSheet() = intent { reduce { state.copy(showAgreementBottomSheet = false) } }

  fun toggleTermChecked() = intent { reduce { state.copy(isCheckedTerm = !state.isCheckedTerm) } }
  fun togglePersonalPolicyChecked() = intent { reduce { state.copy(isCheckedPersonalPolicy = !state.isCheckedPersonalPolicy) } }

  fun openTermWebSite() = intent { postSideEffect(LoginSideEffect.OpenTermWebSite) }
  fun openPersonalPolicyWebSite() = intent { postSideEffect(LoginSideEffect.OpenPersonalPolicyWebSite) }
}
