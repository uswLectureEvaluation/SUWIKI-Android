package com.suwiki.feature.signup

data class SignupState(
  val idState: IdState = IdState(),
  val passwordState: PasswordState = PasswordState(),
  val emailState: EmailState = EmailState(),
  val isLoading: Boolean = false,
) {
  val titleResId = when {
    idState.showIdTextField -> R.string.signup_screen_title_id
    passwordState.showPasswordTextField -> R.string.signup_screen_title_password
    passwordState.showPasswordConfirmTextField -> R.string.signup_screen_title_password_confirm
    else -> R.string.signup_screen_title_email
  }
}

data class IdState(
  val showIdTextField: Boolean = true,
  val showIdCheckButton: Boolean = false,
  val id: String = "",
  private val showIdInvalidHelperText: Boolean = false,
  private val showIdOverlapHelperText: Boolean = false,
) {
  val idHelperTextResId = when {
    showIdInvalidHelperText -> R.string.signup_screen_id_textfield_invalid_helper_text
    showIdOverlapHelperText -> R.string.signup_screen_id_textfield_overlap_helper_text
    else -> R.string.word_empty
  }

  val isErrorIdTextField = showIdInvalidHelperText || showIdOverlapHelperText
}

data class PasswordState(
  val showPasswordTextField: Boolean = false,
  val showPasswordConfirmTextField: Boolean = false,
  val password: String = "",
  val passwordConfirm: String = "",
  private val showPasswordInvalidHelperText: Boolean = false,
  private val showPasswordConfirmInvalidHelperText: Boolean = false,
  private val showPasswordConfirmNotSameHelperText: Boolean = false,
) {
  val passwordHelperTextResId = when {
    showPasswordInvalidHelperText -> R.string.signup_screen_password_textfield_invalid_helper_text
    else -> R.string.word_empty
  }

  val isErrorPasswordTextField = showPasswordInvalidHelperText


  val passwordConfirmHelperTextResId = when {
    showPasswordConfirmInvalidHelperText -> R.string.signup_screen_password_textfield_invalid_helper_text
    showPasswordConfirmNotSameHelperText -> R.string.signup_screen_password_textfield_not_same_helper_text
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
    showEmailNoticeHelperText -> R.string.signup_screen_email_textfield_notice_text
    showEmailInvalidHelperText -> R.string.signup_screen_email_textfield_invalid_text
    else -> R.string.word_empty
  }

  val isErrorEmailTextField = showEmailInvalidHelperText
}

sealed interface SignupSideEffect {
  data class HandleException(val throwable: Throwable) : SignupSideEffect
  data object PopBackStack : SignupSideEffect
  data object NavigateLogin : SignupSideEffect
}
