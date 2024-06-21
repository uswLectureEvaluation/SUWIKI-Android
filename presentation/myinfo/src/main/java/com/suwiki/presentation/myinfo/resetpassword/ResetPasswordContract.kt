package com.suwiki.presentation.myinfo.resetpassword

import com.suwiki.presentation.myinfo.R

data class ResetPasswordState(
  val isLoading: Boolean = false,
  val currentPassword: String = "",
  val newPassword: String = "",
  val checkNewPassword: String = "",
  val showCurrentPassword: Boolean = false,
  val showNewPassword: Boolean = false,
  val showCheckNewPassword: Boolean = false,
  val showPasswordInvalidErrorText: Boolean = false,
  val showPasswordNotMatchErrorText: Boolean = false,
  val showResetPasswordDialog: Boolean = false,
) {
  val passwordInvalidHelperTextId =
    if (showPasswordInvalidErrorText) {
      R.string.textfield_password_invalid_helper_text
    } else {
      R.string.word_empty
    }
  val passwordMatchHelperTextId =
    if (showPasswordNotMatchErrorText) {
      R.string.textfield_password_not_same_helper_text
    } else {
      R.string.word_empty
    }
  val showResetPasswordButton = (!showPasswordInvalidErrorText && !showPasswordNotMatchErrorText) &&
    (currentPassword.isNotEmpty() && newPassword.isNotEmpty() && checkNewPassword.isNotEmpty())
}

sealed interface ResetPasswordSideEffect {
  data object PopBackStack : ResetPasswordSideEffect
  data object NavigateFindPassword : ResetPasswordSideEffect
  data object NavigateLogin : ResetPasswordSideEffect
  data class HandleException(val throwable: Throwable) : ResetPasswordSideEffect
}
