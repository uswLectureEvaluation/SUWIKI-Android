package com.suwiki.feature.login.findid

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
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.util.LaunchedEffectWithLifecycle
import com.suwiki.core.ui.util.TEXT_FIELD_DEBOUNCE
import com.suwiki.feature.login.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(FlowPreview::class)
@Composable
fun FindIdRoute(
  viewModel: FindIdViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is FindIdSideEffect.HandleException -> handleException(sideEffect.throwable)
      FindIdSideEffect.PopBackStack -> popBackStack()
    }
  }

  LaunchedEffectWithLifecycle(key1 = uiState.email) {
    snapshotFlow { uiState.email }
      .debounce(TEXT_FIELD_DEBOUNCE)
      .onEach(viewModel::checkEmailInvalid)
      .launchIn(this)
  }

  FindIdScreen(
    uiState = uiState,
    onClickBack = viewModel::popBackStack,
    onValueChangeEmail = viewModel::updateEmail,
    onClickEmailTextFieldClearButton = { viewModel.updateEmail("") },
    onClickFindIdButton = viewModel::findId,
    onClickFindIdSuccessDialogConfirmButton = {
      viewModel.hideSuccessDialog()
      viewModel.popBackStack()
    },
    onDismissRequestFindIdSuccessDialog = viewModel::hideSuccessDialog,
  )
}

@Composable
fun FindIdScreen(
  uiState: FindIdState = FindIdState(),
  onClickBack: () -> Unit = {},
  onValueChangeEmail: (String) -> Unit = {},
  onClickEmailTextFieldClearButton: () -> Unit = {},
  onClickFindIdButton: () -> Unit = {},
  onClickFindIdSuccessDialogConfirmButton: () -> Unit = {},
  onDismissRequestFindIdSuccessDialog: () -> Unit = {},
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
          text = stringResource(id = R.string.word_find_id),
          style = SuwikiTheme.typography.header1,
        )

        Spacer(modifier = Modifier.size(26.dp))

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

        if (uiState.showFindIdButton) {
          SuwikiContainedLargeButton(
            modifier = Modifier.imePadding(),
            text = stringResource(R.string.word_find_id),
            onClick = onClickFindIdButton,
          )
        }
      }
    }

    if (uiState.showFindIdSuccessDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.find_id_screen_dialog_success_title),
        bodyText = stringResource(R.string.find_id_screen_dialog_success_body),
        confirmButtonText = stringResource(R.string.word_confirm),
        onDismissRequest = onDismissRequestFindIdSuccessDialog,
        onClickConfirm = onClickFindIdSuccessDialogConfirmButton,
      )
    }

    if (uiState.isLoading) {
      LoadingScreen()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FindIdScreenPreview() {
  SuwikiTheme {
    FindIdScreen()
  }
}
