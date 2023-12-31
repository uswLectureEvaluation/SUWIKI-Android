package com.suwiki.feature.login

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
fun LoginRoute(
  viewModel: LoginViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  navigateFindId: () -> Unit,
  navigateFindPassword: () -> Unit,
  navigateSignup: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is LoginSideEffect.HandleException -> handleException(sideEffect.throwable)
      LoginSideEffect.NavigateFindId -> navigateFindId()
      LoginSideEffect.NavigateFindPassword -> navigateFindPassword()
      LoginSideEffect.NavigateSignUp -> navigateSignup()
      LoginSideEffect.PopBackStack -> popBackStack()
    }
  }

  LoginScreen(
    uiState = uiState,
    onValueChangeIdTextField = viewModel::updateId,
    onValueChangePasswordTextField = viewModel::updatePassword,
    onClickIdClearButton = { viewModel.updateId("") },
    onClickPasswordClearButton = { viewModel.updatePassword("") },
    onClickPasswordEyeIcon = viewModel::toggleShowPassword,
    onClickFindIdText = { /* TODO */ },
    onClickFindPasswordText = { /* TODO */ },
    onClickSignupText = { /* TODO */ },
    onClickLoginButton = viewModel::login,
    onClickLoginFailDialogButton = viewModel::hideLoginFailDialog,
    onClickEmailNotAuthDialogButton = viewModel::hideEmailNotAuthDialog,
  )
}

@Composable
fun LoginScreen(
  uiState: LoginState = LoginState(),
  onValueChangeIdTextField: (String) -> Unit = {},
  onValueChangePasswordTextField: (String) -> Unit = {},
  onClickIdClearButton: () -> Unit = {},
  onClickPasswordClearButton: () -> Unit = {},
  onClickPasswordEyeIcon: () -> Unit = {},
  onClickFindIdText: () -> Unit = {},
  onClickFindPasswordText: () -> Unit = {},
  onClickSignupText: () -> Unit = {},
  onClickLoginButton: () -> Unit = {},
  onClickLoginFailDialogButton: () -> Unit = {},
  onClickEmailNotAuthDialogButton: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 63.dp, bottom = 30.dp, start = 24.dp, end = 24.dp),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = stringResource(R.string.word_login), style = SuwikiTheme.typography.header1)

      Spacer(modifier = Modifier.size(26.dp))

      SuwikiRegularTextField(
        value = uiState.id,
        onValueChange = onValueChangeIdTextField,
        onClickClearButton = onClickIdClearButton,
        label = stringResource(R.string.word_id),
        placeholder = stringResource(R.string.login_screen_id_textfield_placeholder),
      )

      Spacer(modifier = Modifier.size(20.dp))

      SuwikiRegularTextField(
        value = uiState.password,
        onValueChange = onValueChangePasswordTextField,
        onClickClearButton = onClickPasswordClearButton,
        showEyeIcon = true,
        showValue = uiState.showPassword,
        onClickEyeIcon = onClickPasswordEyeIcon,
        label = stringResource(R.string.word_password),
        placeholder = stringResource(R.string.login_screen_password_textfield_placeholder),
      )

      Spacer(modifier = Modifier.weight(1f))

      Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          modifier = Modifier.suwikiClickable(onClick = onClickFindIdText),
          text = stringResource(R.string.login_screen_find_id),
          style = SuwikiTheme.typography.body5,
          color = Gray6A,
        )
        VerticalDivider(
          modifier = Modifier.padding(vertical = 4.5.dp),
          thickness = 1.dp,
          color = GrayF6,
        )
        Text(
          modifier = Modifier.suwikiClickable(onClick = onClickFindPasswordText),
          text = stringResource(R.string.login_screen_find_password),
          style = SuwikiTheme.typography.body5,
          color = Gray6A,
        )
        VerticalDivider(
          modifier = Modifier.padding(vertical = 4.5.dp),
          thickness = 1.dp,
          color = GrayF6,
        )
        Text(
          modifier = Modifier.suwikiClickable(onClick = onClickSignupText),
          text = stringResource(R.string.word_signup),
          style = SuwikiTheme.typography.body4,
          color = Primary,
        )
      }

      Spacer(modifier = Modifier.size(20.dp))

      SuwikiContainedLargeButton(
        modifier = Modifier.imePadding(),
        clickable = uiState.loginButtonEnable,
        enabled = uiState.loginButtonEnable,
        text = stringResource(R.string.word_login),
        onClick = onClickLoginButton,
      )
    }

    if (uiState.showLoginFailDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.login_screen_dialog_login_fail_title),
        bodyText = stringResource(R.string.login_screen_dialog_login_fail_body),
        confirmButtonText = stringResource(R.string.word_confirm),
        onDismissRequest = onClickLoginFailDialogButton,
        onClickConfirm = onClickLoginFailDialogButton,
      )
    }

    if (uiState.showEmailNotAuthDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.login_screen_dialog_email_not_auth_title),
        bodyText = stringResource(R.string.login_screen_dialog_email_not_auth_body),
        confirmButtonText = stringResource(R.string.word_confirm),
        onDismissRequest = onClickEmailNotAuthDialogButton,
        onClickConfirm = onClickEmailNotAuthDialogButton,
      )
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
    LoginScreen()
  }
}
