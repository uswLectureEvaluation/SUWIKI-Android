package com.suwiki.feature.login.findpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.feature.common.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.feature.common.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.feature.common.designsystem.component.dialog.SuwikiDialog
import com.suwiki.feature.common.designsystem.component.loading.LoadingScreen
import com.suwiki.feature.common.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.util.LaunchedEffectWithLifecycle
import com.suwiki.feature.common.ui.util.TEXT_FIELD_DEBOUNCE
import com.suwiki.feature.login.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(FlowPreview::class)
@Composable
fun FindPasswordRoute(
  viewModel: FindPasswordViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is FindPasswordSideEffect.HandleException -> handleException(sideEffect.throwable)
      FindPasswordSideEffect.PopBackStack -> popBackStack()
    }
  }

  LaunchedEffectWithLifecycle(key1 = uiState.id) {
    snapshotFlow { uiState.id }
      .debounce(TEXT_FIELD_DEBOUNCE)
      .onEach(viewModel::checkIdInvalid)
      .launchIn(this)
  }

  LaunchedEffectWithLifecycle(key1 = uiState.email) {
    snapshotFlow { uiState.email }
      .debounce(TEXT_FIELD_DEBOUNCE)
      .onEach(viewModel::checkEmailInvalid)
      .launchIn(this)
  }

  FindPasswordScreen(
    uiState = uiState,
    onClickBack = viewModel::popBackStack,
    onValueChangeEmail = viewModel::updateEmail,
    onClickEmailTextFieldClearButton = { viewModel.updateEmail("") },
    onValueChangeId = viewModel::updateId,
    onClickIdTextFieldClearButton = { viewModel.updateId("") },
    onClickFindPasswordButton = viewModel::findPassword,
    onClickFindPasswordSuccessDialogConfirmButton = {
      viewModel.hideSuccessDialog()
      viewModel.popBackStack()
    },
    onDismissRequestFindPasswordSuccessDialog = viewModel::hideSuccessDialog,
  )
}

@Composable
fun FindPasswordScreen(
  uiState: FindPasswordState = FindPasswordState(),
  onClickBack: () -> Unit = {},
  onValueChangeEmail: (String) -> Unit = {},
  onClickEmailTextFieldClearButton: () -> Unit = {},
  onValueChangeId: (String) -> Unit = {},
  onClickIdTextFieldClearButton: () -> Unit = {},
  onClickFindPasswordButton: () -> Unit = {},
  onClickFindPasswordSuccessDialogConfirmButton: () -> Unit = {},
  onDismissRequestFindPasswordSuccessDialog: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    Column {
      SuwikiAppBarWithTitle(
        showCloseIcon = false,
        onClickBack = onClickBack,
      )
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(
            top = 13.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = 20.dp,
          ),
      ) {
        Text(
          text = stringResource(id = R.string.word_find_password),
          style = SuwikiTheme.typography.header1,
        )

        Spacer(modifier = Modifier.size(26.dp))

        SuwikiRegularTextField(
          label = stringResource(R.string.word_id),
          placeholder = stringResource(R.string.textfield_id_placeholder),
          value = uiState.id,
          onValueChange = onValueChangeId,
          onClickClearButton = onClickIdTextFieldClearButton,
          helperText = stringResource(id = uiState.idHelperTextResId),
          isError = uiState.isErrorIdTextField,
        )

        Spacer(modifier = Modifier.size(4.dp))

        SuwikiRegularTextField(
          label = stringResource(R.string.word_email),
          placeholder = stringResource(R.string.textfield_email_placeholder),
          value = uiState.email,
          onValueChange = onValueChangeEmail,
          onClickClearButton = onClickEmailTextFieldClearButton,
          helperText = stringResource(id = uiState.emailHelperTextResId),
          isError = uiState.isErrorEmailTextField,
        )

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.showFindPasswordButton) {
          SuwikiContainedLargeButton(
            modifier = Modifier.imePadding(),
            text = stringResource(R.string.word_find_password),
            onClick = onClickFindPasswordButton,
          )
        }
      }
    }

    if (uiState.showFindPasswordSuccessDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.find_password_screen_dialog_success_title),
        bodyText = stringResource(R.string.find_password_screen_dialog_success_body),
        confirmButtonText = stringResource(R.string.word_confirm),
        onDismissRequest = onDismissRequestFindPasswordSuccessDialog,
        onClickConfirm = onClickFindPasswordSuccessDialogConfirmButton,
      )
    }

    if (uiState.isLoading) {
      LoadingScreen()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FindPasswordScreenPreview() {
  SuwikiTheme {
    FindPasswordScreen()
  }
}
