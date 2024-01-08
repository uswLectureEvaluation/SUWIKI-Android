package com.suwiki.feature.login.findid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.theme.SuwikiTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

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

  FindIdScreen(
    uiState = uiState,
  )
}

@Composable
fun FindIdScreen(
  uiState: FindIdState = FindIdState(),
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 63.dp, bottom = 30.dp, start = 24.dp, end = 24.dp),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = "아이디 찾기")


      Spacer(modifier = Modifier.size(20.dp))

//      SuwikiContainedLargeButton(
//        modifier = Modifier.imePadding(),
//        clickable = uiState.FindIdButtonEnable,
//        enabled = uiState.FindIdButtonEnable,
//        text = stringResource(R.string.word_FindId),
//        onClick = onClickFindIdButton,
//      )
//    }

//    if (uiState.showFindIdFailDialog) {
//      SuwikiDialog(
//        headerText = stringResource(R.string.FindId_screen_dialog_FindId_fail_title),
//        bodyText = stringResource(R.string.FindId_screen_dialog_FindId_fail_body),
//        confirmButtonText = stringResource(R.string.word_confirm),
//        onDismissRequest = onClickFindIdFailDialogButton,
//        onClickConfirm = onClickFindIdFailDialogButton,
//      )
//    }
//
//    if (uiState.showEmailNotAuthDialog) {
//      SuwikiDialog(
//        headerText = stringResource(R.string.FindId_screen_dialog_email_not_auth_title),
//        bodyText = stringResource(R.string.FindId_screen_dialog_email_not_auth_body),
//        confirmButtonText = stringResource(R.string.word_confirm),
//        onDismissRequest = onClickEmailNotAuthDialogButton,
//        onClickConfirm = onClickEmailNotAuthDialogButton,
//      )
//    }

      if (uiState.isLoading) {
        LoadingScreen()
      }
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
