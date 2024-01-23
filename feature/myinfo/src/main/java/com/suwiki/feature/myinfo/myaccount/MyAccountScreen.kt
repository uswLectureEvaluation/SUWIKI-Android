package com.suwiki.feature.myinfo.myaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyAccountRoute(
  viewModel: MyAccountViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  navigateResetPassword: () -> Unit = {},
  navigateQuit: () -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyAccountSideEffect.PopBackStack -> popBackStack()
      is MyAccountSideEffect.NavigateResetPassword -> navigateResetPassword()
      is MyAccountSideEffect.NavigateQuit -> navigateQuit()
      is MyAccountSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  MyAccountScreen(
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
    onClickResetPassword = viewModel::navigateResetPassword,
    onClickLogout = viewModel::logout,
    onClickQuit = viewModel::navigateQuit,
  )
}

@Composable
fun MyAccountScreen(
  uiState: MyAccountState = MyAccountState(),
  popBackStack: () -> Unit = {},
  onClickResetPassword: () -> Unit = {},
  onClickLogout: () -> Unit = {},
  onClickQuit: () -> Unit = {},
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
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 14.dp),
      text = stringResource(R.string.word_my_account),
      style = SuwikiTheme.typography.body2,
    )
    UserInfoRowContainer(
      userInfo = stringResource(R.string.word_login_id),
      userInfoValue = uiState.userId,
    )
    UserInfoRowContainer(
      userInfo = stringResource(R.string.word__authentication_email),
      userInfoValue = uiState.userEmail,
    )
    Spacer(modifier = Modifier.height(24.dp))
    AlignContainer(
      title = stringResource(R.string.word_reset_password),
      onClick = onClickResetPassword,
    )
    AlignContainer(
      title = stringResource(R.string.word_logout),
      onClick = onClickLogout,
    )
    AlignContainer(
      title = stringResource(R.string.word_quit),
      onClick = onClickQuit,
    )
  }
}

@Composable
fun AlignContainer(
  modifier: Modifier = Modifier,
  title: String,
  onClick: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .suwikiClickable(onClick = onClick)
      .padding(horizontal = 24.dp, vertical = 14.dp),
  ) {
    Text(
      text = title,
      style = SuwikiTheme.typography.body2,
    )
  }
}

@Composable
fun UserInfoRowContainer(
  modifier: Modifier = Modifier,
  userInfo: String,
  userInfoValue: String,
) {
  Column {
    Row(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = userInfo,
        style = SuwikiTheme.typography.caption2,
        color = Gray95,
      )
      Text(
        text = userInfoValue,
        style = SuwikiTheme.typography.body5,
      )
    }
    HorizontalDivider(color = GrayF6)
  }
}

@Preview
@Composable
fun MyAccountScreenPreview() {
  SuwikiTheme {
    MyAccountScreen()
  }
}
