package com.suwiki.feature.myinfo.resetpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.core.ui.util.LaunchedEffectWithLifecycle
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ResetPasswordRoute(
  viewModel: ResetPasswordViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  navigateFindPassword: () -> Unit,
  navigateLogin: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is ResetPasswordSideEffect.PopBackStack -> popBackStack()
      is ResetPasswordSideEffect.NavigateFindPassword -> navigateFindPassword()
      is ResetPasswordSideEffect.NavigateLogin -> navigateLogin()
      is ResetPasswordSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  LaunchedEffectWithLifecycle(key1 = uiState.newPassword) {
    viewModel.checkNewPasswordInvalid(uiState.newPassword)
  }

  LaunchedEffectWithLifecycle(key1 = uiState.checkNewPassword) {
    viewModel.checkNewPasswordMatch(uiState.checkNewPassword)
  }

  ResetPasswordScreen(
    uiState = uiState,
    onClickBack = viewModel::popBackStack,
    onValueChangeCurrentPassword = viewModel::updateCurrentPassword,
    onClickCurrentPasswordTextFieldClearButton = { viewModel.updateCurrentPassword("") },
    onClickCurrentPasswordEyeIcon = viewModel::toggleShowCurrentPassword,
    onValueChangeNewPassword = viewModel::updateNewPassword,
    onClickNewPasswordTextFieldClearButton = { viewModel.updateNewPassword("") },
    onClickNewPasswordEyeIcon = viewModel::toggleShowNewPassword,
    onValueChangeCheckNewPassword = viewModel::updateCheckNewPassword,
    onClickCheckNewPasswordTextFieldClearButton = { viewModel.updateCheckNewPassword("") },
    onClickCheckNewPasswordEyeIcon = viewModel::toggleShowCheckNewPassword,
    onClickFindPasswordButton = viewModel::navigateFindPassword,
    onClickResetPasswordButton = viewModel::resetPassword,
    onDismissResetPasswordDialog = viewModel::hideResetPasswordDialog,
    onClickResetPasswordDialogConfirm = viewModel::navigateLogin,
  )
}

@Composable
fun ResetPasswordScreen(
  uiState: ResetPasswordState = ResetPasswordState(),
  onClickBack: () -> Unit = {},
  onValueChangeCurrentPassword: (String) -> Unit = {},
  onClickCurrentPasswordTextFieldClearButton: () -> Unit = {},
  onClickCurrentPasswordEyeIcon: () -> Unit = {},
  onClickNewPasswordEyeIcon: () -> Unit = {},
  onClickCheckNewPasswordEyeIcon: () -> Unit = {},
  onValueChangeNewPassword: (String) -> Unit = {},
  onClickNewPasswordTextFieldClearButton: () -> Unit = {},
  onValueChangeCheckNewPassword: (String) -> Unit = {},
  onClickCheckNewPasswordTextFieldClearButton: () -> Unit = {},
  onClickFindPasswordButton: () -> Unit = {},
  onClickResetPasswordButton: () -> Unit = {},
  onDismissResetPasswordDialog: () -> Unit = {},
  onClickResetPasswordDialogConfirm: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White)
      .padding(bottom = 20.dp),
  ) {
    SuwikiAppBarWithTitle(
      showBackIcon = true,
      showCloseIcon = false,
      onClickBack = onClickBack,
    )
    Column(
      modifier = Modifier.padding(start = 24.dp, end = 24.dp),
    ) {
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 14.dp),
        text = stringResource(R.string.word_reset_password),
        style = SuwikiTheme.typography.header1,
      )
      Spacer(modifier = Modifier.height(12.dp))
      SuwikiRegularTextField(
        label = stringResource(R.string.word_current_password),
        placeholder = stringResource(R.string.reset_password_screen_current_password_textfield_placeholder),
        showEyeIcon = true,
        value = uiState.currentPassword,
        showValue = uiState.showCurrentPassword,
        onValueChange = onValueChangeCurrentPassword,
        onClickClearButton = onClickCurrentPasswordTextFieldClearButton,
        onClickEyeIcon = onClickCurrentPasswordEyeIcon,
        helperText = "",
      )
      Spacer(modifier = Modifier.height(12.dp))
      SuwikiRegularTextField(
        label = stringResource(R.string.word_new_password),
        placeholder = stringResource(R.string.textfield_password_placeholder),
        showEyeIcon = true,
        value = uiState.newPassword,
        showValue = uiState.showNewPassword,
        onValueChange = onValueChangeNewPassword,
        onClickClearButton = onClickNewPasswordTextFieldClearButton,
        onClickEyeIcon = onClickNewPasswordEyeIcon,
        helperText = stringResource(uiState.passwordInvalidHelperTextId),
        isError = uiState.showPasswordInvalidErrorText,
      )
      Spacer(modifier = Modifier.height(12.dp))
      SuwikiRegularTextField(
        label = stringResource(R.string.word_new_password_check),
        placeholder = stringResource(R.string.reset_password_screen_check_new_password_textfield_placeholder),
        showEyeIcon = true,
        value = uiState.checkNewPassword,
        showValue = uiState.showCheckNewPassword,
        onValueChange = onValueChangeCheckNewPassword,
        onClickClearButton = onClickCheckNewPasswordTextFieldClearButton,
        onClickEyeIcon = onClickCheckNewPasswordEyeIcon,
        helperText = stringResource(uiState.passwordMatchHelperTextId),
        isError = uiState.showPasswordNotMatchErrorText,
      )
      Spacer(modifier = Modifier.weight(1f))
      Column(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          modifier = Modifier
            .suwikiClickable(onClick = onClickFindPasswordButton),
          text = stringResource(R.string.word_find_password),
          style = SuwikiTheme.typography.body5,
          color = Gray6A,
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (uiState.showResetPasswordButton) {
          SuwikiContainedLargeButton(
            text = stringResource(R.string.word_change),
            onClick = onClickResetPasswordButton,
          )
        }
      }
    }
  }
  if (uiState.showResetPasswordDialog) {
    SuwikiDialog(
      headerText = stringResource(R.string.reset_password_screen_dialog_header),
      bodyText = stringResource(R.string.reset_password_screen_dialog_body),
      confirmButtonText = stringResource(R.string.word_login),
      dismissButtonText = stringResource(R.string.word_cancel),
      onDismissRequest = onDismissResetPasswordDialog,
      onClickConfirm = onClickResetPasswordDialogConfirm,
    )
  }
  if (uiState.isLoading) {
    LoadingScreen()
  }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
  SuwikiTheme {
    ResetPasswordScreen()
  }
}
