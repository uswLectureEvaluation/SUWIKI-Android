package com.suwiki.feature.login.findpassword

import com.suwiki.feature.login.R

data class FindPasswordState(
  val email: String = "",
  val showFindPasswordButton: Boolean = false,
  val showFindPasswordSuccessDialog: Boolean = false,
  private val showEmailNoticeHelperText: Boolean = true,
  private val showEmailInvalidHelperText: Boolean = false,
  val id: String = "",
  private val showIdOverlapHelperText: Boolean = false,
  private val showIdInvalidHelperText: Boolean = false,
  val isLoading: Boolean = false,
) {
  val emailHelperTextResId = when {
    showEmailNoticeHelperText -> R.string.textfield_email_notice_text
    showEmailInvalidHelperText -> R.string.textfield_email_invalid_text
    else -> R.string.word_empty
  }

  val isErrorEmailTextField = showEmailInvalidHelperText

  val idHelperTextResId = when {
    showIdInvalidHelperText -> R.string.textfield_id_invalid_helper_text
    showIdOverlapHelperText -> R.string.textfield_id_overlap_helper_text
    else -> R.string.word_empty
  }

  val isErrorIdTextField = showIdInvalidHelperText || showIdOverlapHelperText
}

sealed interface FindPasswordSideEffect {
  data class HandleException(val throwable: Throwable) : FindPasswordSideEffect
  data object PopBackStack : FindPasswordSideEffect
}
