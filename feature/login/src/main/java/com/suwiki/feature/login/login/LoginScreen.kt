package com.suwiki.feature.login.login

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiAgreementBottomSheet
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.ui.extension.suwikiClickable
import com.suwiki.feature.common.ui.util.PRIVACY_POLICY_SITE
import com.suwiki.feature.common.ui.util.TERMS_SITE
import com.suwiki.feature.login.R
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
  val uriHandler = LocalUriHandler.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is LoginSideEffect.HandleException -> handleException(sideEffect.throwable)
      LoginSideEffect.NavigateFindId -> navigateFindId()
      LoginSideEffect.NavigateFindPassword -> navigateFindPassword()
      LoginSideEffect.NavigateSignUp -> navigateSignup()
      LoginSideEffect.PopBackStack -> popBackStack()
      LoginSideEffect.OpenPersonalPolicyWebSite -> uriHandler.openUri(PRIVACY_POLICY_SITE)
      LoginSideEffect.OpenTermWebSite -> uriHandler.openUri(TERMS_SITE)
    }
  }

  LoginScreen(
    uiState = uiState,
    onValueChangeIdTextField = viewModel::updateId,
    onValueChangePasswordTextField = viewModel::updatePassword,
    onClickIdClearButton = { viewModel.updateId("") },
    onClickPasswordClearButton = { viewModel.updatePassword("") },
    onClickPasswordEyeIcon = viewModel::toggleShowPassword,
    onClickFindIdText = viewModel::navigateFindId,
    onClickFindPasswordText = viewModel::navigateFindPassword,
    onClickSignupText = viewModel::showAgreementBottomSheet,
    onClickLoginButton = viewModel::login,
    onClickLoginFailDialogButton = viewModel::hideLoginFailDialog,
    onClickEmailNotAuthDialogButton = viewModel::hideEmailNotAuthDialog,
    onClickTermCheckIcon = viewModel::toggleTermChecked,
    onClickTermArrowIcon = viewModel::openTermWebSite,
    onClickPersonalCheckIcon = viewModel::togglePersonalPolicyChecked,
    onClickPersonalArrowIcon = viewModel::openPersonalPolicyWebSite,
    onClickAgreementButton = {
      viewModel.hideAgreementBottomSheet()
      viewModel.navigateSignup()
    },
    hideAgreementBottomSheet = viewModel::hideAgreementBottomSheet,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
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
  hideAgreementBottomSheet: () -> Unit = {},
  onClickTermCheckIcon: () -> Unit = {},
  onClickTermArrowIcon: () -> Unit = {},
  onClickPersonalCheckIcon: () -> Unit = {},
  onClickPersonalArrowIcon: () -> Unit = {},
  onClickAgreementButton: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 63.dp, bottom = 30.dp, start = 24.dp, end = 24.dp),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = stringResource(com.suwiki.feature.common.ui.R.string.word_login), style = SuwikiTheme.typography.header1)

      Spacer(modifier = Modifier.size(26.dp))

      SuwikiRegularTextField(
        value = uiState.id,
        onValueChange = onValueChangeIdTextField,
        onClickClearButton = onClickIdClearButton,
        label = stringResource(com.suwiki.feature.common.ui.R.string.word_id),
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
        label = stringResource(com.suwiki.feature.common.ui.R.string.word_password),
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
          text = stringResource(com.suwiki.feature.common.ui.R.string.word_find_id),
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
          text = stringResource(com.suwiki.feature.common.ui.R.string.word_find_password),
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
          text = stringResource(com.suwiki.feature.common.ui.R.string.word_signup),
          style = SuwikiTheme.typography.body4,
          color = Primary,
        )
      }

      Spacer(modifier = Modifier.size(20.dp))

      SuwikiContainedLargeButton(
        modifier = Modifier.imePadding(),
        clickable = uiState.loginButtonEnable,
        enabled = uiState.loginButtonEnable,
        text = stringResource(com.suwiki.feature.common.ui.R.string.word_login),
        onClick = onClickLoginButton,
      )
    }

    if (uiState.showLoginFailDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.login_screen_dialog_login_fail_title),
        bodyText = stringResource(R.string.login_screen_dialog_login_fail_body),
        confirmButtonText = stringResource(com.suwiki.feature.common.ui.R.string.word_confirm),
        onDismissRequest = onClickLoginFailDialogButton,
        onClickConfirm = onClickLoginFailDialogButton,
      )
    }

    if (uiState.showEmailNotAuthDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.login_screen_dialog_email_not_auth_title),
        bodyText = stringResource(R.string.login_screen_dialog_email_not_auth_body),
        confirmButtonText = stringResource(com.suwiki.feature.common.ui.R.string.word_confirm),
        onDismissRequest = onClickEmailNotAuthDialogButton,
        onClickConfirm = onClickEmailNotAuthDialogButton,
      )
    }

    if (uiState.isLoading) {
      LoadingScreen()
    }

    SuwikiAgreementBottomSheet(
      isSheetOpen = uiState.showAgreementBottomSheet,
      buttonEnabled = uiState.isEnabledAgreementButton,
      isCheckedTerm = uiState.isCheckedTerm,
      onClickTermCheckIcon = onClickTermCheckIcon,
      onClickTermArrowIcon = onClickTermArrowIcon,
      isCheckedPersonalPolicy = uiState.isCheckedPersonalPolicy,
      onClickPersonalCheckIcon = onClickPersonalCheckIcon,
      onClickPersonalArrowIcon = onClickPersonalArrowIcon,
      onClickAgreementButton = onClickAgreementButton,
      onDismissRequest = hideAgreementBottomSheet,
    )
  }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  SuwikiTheme {
    LoginScreen()
  }
}
