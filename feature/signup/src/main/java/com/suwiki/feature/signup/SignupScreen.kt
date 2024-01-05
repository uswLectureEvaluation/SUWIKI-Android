package com.suwiki.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignupRoute(
  viewModel: SignupViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  navigateLogin: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is SignupSideEffect.HandleException -> handleException(sideEffect.throwable)
      SignupSideEffect.NavigateLogin -> navigateLogin()
      SignupSideEffect.PopBackStack -> popBackStack()
    }
  }

  SignupScreen(
    uiState = uiState,
  )
}

@Composable
fun SignupScreen(
  uiState: SignupState = SignupState(),
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
  ) {
    Column(
      modifier = Modifier.padding(top = 63.dp, start = 24.dp, end = 24.dp, bottom = 20.dp),
    ) {
      Text(
        text = stringResource(id = uiState.titleResId),
        style = SuwikiTheme.typography.header1,
      )

      Spacer(modifier = Modifier.size(26.dp))

      Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        with(uiState.emailState) {
          if (showEmailTextField) {
            SuwikiRegularTextField(
              label = stringResource(R.string.word_email),
              placeholder = stringResource(R.string.signup_screen_email_textfield_placeholder),
              value = email,
              helperText = stringResource(id = emailHelperTextResId),
              isError = isErrorEmailTextField,
            )
          }
        }

        with(uiState.passwordState) {
          if (showPasswordConfirmTextField) {
            SuwikiRegularTextField(
              label = stringResource(R.string.word_password_confirm),
              placeholder = stringResource(R.string.textfield_password_placeholder),
              value = password,
              helperText = stringResource(id = passwordConfirmHelperTextResId),
              isError = isErrorPasswordConfirmTextField,
              showEyeIcon = true,
            )
          }

          if (showPasswordTextField) {
            SuwikiRegularTextField(
              label = stringResource(R.string.word_password),
              placeholder = stringResource(R.string.textfield_password_placeholder),
              value = password,
              helperText = stringResource(id = passwordHelperTextResId),
              isError = isErrorPasswordTextField,
              showEyeIcon = true,
            )
          }
        }

        with(uiState.idState) {
          if (showIdTextField) {
            SuwikiRegularTextField(
              label = stringResource(R.string.word_id),
              placeholder = stringResource(R.string.signup_screen_id_textfield_placeholder),
              value = id,
              helperText = stringResource(id = idHelperTextResId),
              isError = isErrorIdTextField,
            )
          }
        }
      }
    }

    if (uiState.emailState.showSendAuthEmailButton) {
      SuwikiContainedLargeButton(text = stringResource(R.string.signup_screen_receive_auth_email))
    }

    if (uiState.idState.showIdCheckButton) {
      SuwikiContainedLargeButton(text = stringResource(R.string.signup_screen_id_overlap))
    }


    if (uiState.isLoading) {
      LoadingScreen()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun LectureEvaluationScreenPreview() {
  SuwikiTheme {
    SignupScreen()
  }
}
