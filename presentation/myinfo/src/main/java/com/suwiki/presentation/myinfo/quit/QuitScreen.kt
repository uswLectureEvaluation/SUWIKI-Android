package com.suwiki.presentation.myinfo.quit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.presentation.common.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.presentation.common.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.presentation.common.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.designsystem.theme.White
import com.suwiki.presentation.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun QuitRoute(
  viewModel: QuitViewModel = hiltViewModel(),
  onShowToast: (String) -> Unit,
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  val context = LocalContext.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is QuitSideEffect.PopBackStack -> popBackStack()
      is QuitSideEffect.HandleException -> handleException(sideEffect.throwable)
      QuitSideEffect.ShowSuccessQuitToast -> onShowToast(context.getString(R.string.quit_screen_quit_success_toast))
    }
  }
  QuitScreen(
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
    onClickQuitButton = viewModel::quit,
    onValueChangeId = viewModel::updateId,
    onClickIdTextFieldClearButton = { viewModel.updateId("") },
    onValueChangePassword = viewModel::updatePassword,
    onClickPasswordTextFieldClearButton = { viewModel.updatePassword("") },
    onClickPasswordEyeIcon = viewModel::toggleShowPassword,
  )
}

@Composable
fun QuitScreen(
  uiState: QuitState = QuitState(),
  popBackStack: () -> Unit = {},
  onClickQuitButton: (String, String) -> Unit = { _, _ -> },
  onValueChangeId: (String) -> Unit = {},
  onClickIdTextFieldClearButton: () -> Unit = {},
  onValueChangePassword: (String) -> Unit = {},
  onClickPasswordTextFieldClearButton: () -> Unit = {},
  onClickPasswordEyeIcon: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTitle(
      showBackIcon = true,
      showCloseIcon = false,
      onClickBack = popBackStack,
    )
    Column(
      modifier = Modifier.padding(start = 24.dp, end = 24.dp),
    ) {
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 14.dp),
        text = stringResource(R.string.word_quit),
        style = SuwikiTheme.typography.header1,
      )
      Spacer(modifier = Modifier.height(12.dp))
      SuwikiRegularTextField(
        label = stringResource(R.string.word_id),
        placeholder = stringResource(R.string.quit_screen_id_textfield_placeholder),
        value = uiState.id,
        onValueChange = onValueChangeId,
        onClickClearButton = onClickIdTextFieldClearButton,
      )
      Spacer(modifier = Modifier.height(12.dp))
      SuwikiRegularTextField(
        label = stringResource(R.string.word_password),
        placeholder = stringResource(R.string.quit_screen_password_textfield_placeholder),
        showEyeIcon = true,
        value = uiState.password,
        showValue = uiState.showPassword,
        onValueChange = onValueChangePassword,
        onClickClearButton = onClickPasswordTextFieldClearButton,
        onClickEyeIcon = onClickPasswordEyeIcon,
      )
      Spacer(modifier = Modifier.weight(1f))
      SuwikiContainedLargeButton(
        modifier = Modifier.imePadding(),
        text = stringResource(R.string.word_quit),
        onClick = { onClickQuitButton(uiState.id, uiState.password) },
        clickable = uiState.quitButtonEnable,
        enabled = uiState.quitButtonEnable,
      )
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Preview
@Composable
fun QuitScreenPreview() {
  SuwikiTheme {
    QuitScreen()
  }
}
