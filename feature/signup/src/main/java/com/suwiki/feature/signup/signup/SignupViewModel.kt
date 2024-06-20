package com.suwiki.feature.signup.signup

import androidx.lifecycle.ViewModel
import com.suwiki.feature.common.ui.util.REGEX
import com.suwiki.domain.signup.usecase.CheckIdOverlapUseCase
import com.suwiki.domain.signup.usecase.SignupUseCase
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
class SignupViewModel @Inject constructor(
  private val checkIdOverlapUseCase: CheckIdOverlapUseCase,
  private val signupUseCase: SignupUseCase,
) : ContainerHost<SignupState, SignupSideEffect>, ViewModel() {
  override val container: Container<SignupState, SignupSideEffect> = container(SignupState())

  private val idState
    get() = container.stateFlow.value.idState

  private var isIdValid: Boolean = false

  private val passwordState
    get() = container.stateFlow.value.passwordState

  private var isPasswordValid: Boolean = false

  private var isPasswordConfirmValid: Boolean = false

  private val emailState
    get() = container.stateFlow.value.emailState

  private var passwordFocused = false
  private var passwordConfirmFocused = false
  private var emailFocused = false

  fun signup() = intent {
    reduce { state.copy(isLoading = true) }
    signupUseCase(
      SignupUseCase.Param(
        id = idState.id,
        password = passwordState.passwordConfirm,
        email = emailState.email,
      ),
    )
      .onSuccess {
        postSideEffect(SignupSideEffect.NavigateSignupComplete)
      }
      .onFailure {
        postSideEffect(SignupSideEffect.HandleException(it))
      }
    reduce { state.copy(isLoading = false) }
  }

  @OptIn(OrbitExperimental::class)
  fun updateEmail(email: String) = blockingIntent {
    reduce {
      state.copy(
        emailState = emailState.copy(
          email = email,
          showSendAuthEmailButton = false,
          showEmailNoticeHelperText = true,
          showEmailInvalidHelperText = false,
        ),
      )
    }
  }

  fun checkEmailInvalid(email: String) {
    when {
      email.isEmpty() -> Unit
      email.matches(REGEX.EMAIL) -> showSendAuthEmailButton()
      else -> showEmailInvalidHelperText()
    }
  }

  private fun showSendAuthEmailButton() = intent {
    reduce {
      state.copy(
        emailState = emailState.copy(
          showSendAuthEmailButton = true,
          showEmailNoticeHelperText = false,
          showEmailInvalidHelperText = false,
        ),
      )
    }
  }

  private fun showEmailInvalidHelperText() = intent {
    reduce {
      state.copy(
        emailState = emailState.copy(
          showSendAuthEmailButton = false,
          showEmailNoticeHelperText = false,
          showEmailInvalidHelperText = true,
        ),
      )
    }
  }

  fun toggleShowPasswordValue() = intent {
    reduce {
      state.copy(
        passwordState = passwordState.copy(
          showPasswordValue = !passwordState.showPasswordValue,
        ),
      )
    }
  }

  fun toggleShowPasswordConfirmValue() = intent {
    reduce {
      state.copy(
        passwordState = passwordState.copy(
          showPasswordConfirmValue = !passwordState.showPasswordConfirmValue,
        ),
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updatePasswordConfirm(passwordConfirm: String) = blockingIntent {
    reduce {
      isPasswordConfirmValid = false
      state.copy(
        passwordState = passwordState.copy(
          passwordConfirm = passwordConfirm,
          showPasswordConfirmInvalidHelperText = false,
          showPasswordConfirmNotSameHelperText = false,
        ),
      )
    }
  }

  fun checkPasswordConfirmInvalid(password: String, passwordConfirm: String) {
    when {
      passwordConfirm.isEmpty() -> Unit
      passwordConfirm.matches(REGEX.PASSWORD) && passwordConfirm == password -> showEmailTextField()
      passwordConfirm != password -> showPasswordConfirmNotSameHelperText()
      else -> showPasswordConfirmInvalidHelperText()
    }
  }

  private fun showEmailTextField() = intent {
    reduce {
      isPasswordConfirmValid = true
      state.copy(
        emailState = emailState.copy(
          showEmailTextField = true,
        ),
        passwordState = passwordState.copy(
          showPasswordTextField = false,
          showPasswordConfirmTextField = false,
          showPasswordConfirmNotSameHelperText = false,
          showPasswordConfirmInvalidHelperText = false,
        ),
        idState = idState.copy(
          showIdTextField = false,
        ),
      )
    }
    if (emailFocused.not()) {
      postSideEffect(SignupSideEffect.FocusEmail)
      emailFocused = true
    }
  }

  private fun showPasswordConfirmNotSameHelperText() = intent {
    reduce {
      state.copy(
        passwordState = passwordState.copy(
          showPasswordConfirmNotSameHelperText = true,
        ),
      )
    }
  }

  private fun showPasswordConfirmInvalidHelperText() = intent {
    reduce {
      state.copy(
        passwordState = passwordState.copy(
          showPasswordConfirmInvalidHelperText = true,
        ),
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updatePassword(password: String) = blockingIntent {
    reduce {
      isPasswordValid = false
      state.copy(
        passwordState = passwordState.copy(
          password = password,
          showPasswordInvalidHelperText = false,
        ),
      )
    }
  }

  fun checkPasswordInvalid(password: String) {
    when {
      password.isEmpty() -> Unit
      password.matches(REGEX.PASSWORD) -> showPasswordConfirmTextField()
      else -> showPasswordInvalidHelperText()
    }
  }

  private fun showPasswordConfirmTextField() = intent {
    reduce {
      isPasswordValid = true
      state.copy(
        passwordState = passwordState.copy(
          showPasswordConfirmTextField = true,
        ),
      )
    }
    if (passwordConfirmFocused.not()) {
      postSideEffect(SignupSideEffect.FocusPasswordConfirm)
      passwordConfirmFocused = true
    }
  }

  private fun showPasswordInvalidHelperText() = intent {
    reduce {
      state.copy(
        passwordState = passwordState.copy(
          showPasswordInvalidHelperText = true,
        ),
      )
    }
  }

  fun checkIdOverlap() = intent {
    reduce { state.copy(isLoading = true) }
    checkIdOverlapUseCase(id = idState.id)
      .onSuccess(::handleCheckIdOverlapSuccess)
      .onFailure {
        postSideEffect(SignupSideEffect.HandleException(it))
      }
    reduce { state.copy(isLoading = false) }
  }

  private fun handleCheckIdOverlapSuccess(overlap: Boolean) = intent {
    if (overlap) {
      reduce { state.copy(idState = idState.copy(showIdOverlapHelperText = true)) }
    } else {
      reduce {
        isIdValid = true
        state.copy(
          passwordState = passwordState.copy(showPasswordTextField = true),
          idState = idState.copy(showIdCheckButton = false),
        )
      }
      if (passwordFocused.not()) {
        postSideEffect(SignupSideEffect.FocusPassword)
        passwordFocused = true
      }
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateId(id: String) = blockingIntent {
    reduce {
      isIdValid = false
      state.copy(
        idState = idState.copy(
          id = id,
          showIdInvalidHelperText = false,
          showIdOverlapHelperText = false,
          showIdCheckButton = false,
        ),
      )
    }
  }

  fun checkIdInvalid(id: String) {
    when {
      id.isEmpty() -> Unit
      id.matches(REGEX.ID) && isIdValid.not() -> showIdCheckButton()
      isIdValid -> hideIdInvalidHelperText()
      else -> showIdInvalidHelperText()
    }
  }

  private fun showIdCheckButton() = intent {
    reduce {
      state.copy(
        idState = idState.copy(
          showIdCheckButton = true,
        ),
      )
    }
  }

  private fun hideIdInvalidHelperText() = intent {
    reduce {
      state.copy(
        idState = idState.copy(
          showIdInvalidHelperText = false,
        ),
      )
    }
  }

  private fun showIdInvalidHelperText() = intent {
    reduce {
      state.copy(
        idState = idState.copy(
          showIdInvalidHelperText = true,
        ),
      )
    }
  }
}
