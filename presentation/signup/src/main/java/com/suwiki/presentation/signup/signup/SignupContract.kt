package com.suwiki.presentation.signup.signup

import com.suwiki.presentation.signup.R

data class SignupState(
  val idState: IdState = IdState(),
  val passwordState: PasswordState = PasswordState(),
  val emailState: EmailState = EmailState(),
  val isLoading: Boolean = false,
) {
  val titleResId = when {
    emailState.showEmailTextField -> R.string.signup_screen_title_email
    passwordState.showPasswordConfirmTextField -> R.string.signup_screen_title_password_confirm
    passwordState.showPasswordTextField -> R.string.signup_screen_title_password
    idState.showIdTextField -> R.string.signup_screen_title_id
    else -> R.string.signup_screen_title_email
  }
}

data class IdState(
  val showIdTextField: Boolean = true,
  val showIdCheckButton: Boolean = false,
  val id: String = "",
  private val showIdOverlapHelperText: Boolean = false,
  private val showIdInvalidHelperText: Boolean = false,
) {
  val idHelperTextResId = when {
    showIdInvalidHelperText -> R.string.textfield_id_invalid_helper_text
    showIdOverlapHelperText -> R.string.textfield_id_overlap_helper_text
    else -> R.string.word_empty
  }

  val isErrorIdTextField = showIdInvalidHelperText || showIdOverlapHelperText
}

data class PasswordState(
  val showPasswordTextField: Boolean = false,
  val showPasswordConfirmTextField: Boolean = false,
  val password: String = "",
  val showPasswordValue: Boolean = false,
  val passwordConfirm: String = "",
  val showPasswordConfirmValue: Boolean = false,
  private val showPasswordInvalidHelperText: Boolean = false,
  private val showPasswordConfirmInvalidHelperText: Boolean = false,
  private val showPasswordConfirmNotSameHelperText: Boolean = false,
) {
  val passwordHelperTextResId = when {
    showPasswordInvalidHelperText -> R.string.textfield_password_invalid_helper_text
    else -> R.string.word_empty
  }

  val isErrorPasswordTextField = showPasswordInvalidHelperText

  val passwordConfirmHelperTextResId = when {
    showPasswordConfirmInvalidHelperText -> R.string.textfield_password_invalid_helper_text
    showPasswordConfirmNotSameHelperText -> R.string.textfield_password_not_same_helper_text
    else -> R.string.word_empty
  }

  val isErrorPasswordConfirmTextField = showPasswordConfirmInvalidHelperText || showPasswordConfirmNotSameHelperText
}

data class EmailState(
  val showEmailTextField: Boolean = false,
  val showSendAuthEmailButton: Boolean = false,
  val email: String = "",
  private val showEmailNoticeHelperText: Boolean = true,
  private val showEmailInvalidHelperText: Boolean = false,
) {
  val emailHelperTextResId = when {
    showEmailNoticeHelperText -> R.string.textfield_email_notice_text
    showEmailInvalidHelperText -> R.string.textfield_email_invalid_text
    else -> R.string.word_empty
  }

  val isErrorEmailTextField = showEmailInvalidHelperText
}

sealed interface SignupSideEffect {
  data class HandleException(val throwable: Throwable) : SignupSideEffect
  data object PopBackStack : SignupSideEffect
  data object NavigateSignupComplete : SignupSideEffect
  data object FocusPassword : SignupSideEffect
  data object FocusPasswordConfirm : SignupSideEffect
  data object FocusEmail : SignupSideEffect
}
