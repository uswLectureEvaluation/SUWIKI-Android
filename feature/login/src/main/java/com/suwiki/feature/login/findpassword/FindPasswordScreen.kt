package com.suwiki.feature.login.findpassword

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
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.core.ui.util.PRIVACY_POLICY_SITE
import com.suwiki.core.ui.util.TERMS_SITE
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

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

  FindPasswordScreen(
    uiState = uiState,
  )
}

@Composable
fun FindPasswordScreen(
  uiState: FindPasswordState = FindPasswordState(),
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 63.dp, bottom = 30.dp, start = 24.dp, end = 24.dp),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = "비번 찾기")
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
