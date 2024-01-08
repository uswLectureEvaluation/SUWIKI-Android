package com.suwiki.feature.login.findid

import com.suwiki.feature.login.R

data class FindIdState(
  val email: String = "",
  val showFindIdButton: Boolean = false,
  val showFindIdSuccessDialog: Boolean = false,
  private val showEmailNoticeHelperText: Boolean = true,
  private val showEmailInvalidHelperText: Boolean = false,
  val isLoading: Boolean = false,
) {
  val emailHelperTextResId = when {
    showEmailNoticeHelperText -> R.string.textfield_email_notice_text
    showEmailInvalidHelperText -> R.string.textfield_email_invalid_text
    else -> R.string.word_empty
  }

  val isErrorEmailTextField = showEmailInvalidHelperText
}

sealed interface FindIdSideEffect {
  data class HandleException(val throwable: Throwable) : FindIdSideEffect
  data object PopBackStack : FindIdSideEffect
}
