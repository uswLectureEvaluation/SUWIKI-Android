package com.suwiki.feature.myinfo.banhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.container.SuwikiBoardContainer
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun BanHistoryRoute(
  viewModel: BanHistoryViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is BanHistorySideEffect.PopBackStack -> popBackStack()
      is BanHistorySideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  BanHistoryScreen(
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
  )
}

@Composable
fun BanHistoryScreen(
  uiState: BanHistoryState = BanHistoryState(),
  popBackStack: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_info_ban_history),
      showBackIcon = true,
      showCloseIcon = false,
      onClickBack = popBackStack,
    )

    if (uiState.showEmptyScreen) {
      Text(
        modifier = Modifier
          .padding(top = 150.dp)
          .fillMaxSize(),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.ban_history_screen_empt),
        style = SuwikiTheme.typography.header4,
        color = Gray95,
      )
    }

    LazyColumn {
      items(items = uiState.banHistory) { banHistory ->
        SuwikiBoardContainer(
          titleText = banHistory.reason,
          dateText = banHistory.createdAt.toString(),
        )
      }
      items(items = uiState.blackList) { blackList ->
        SuwikiBoardContainer(
          titleText = blackList.reason,
          dateText = blackList.createdAt.toString(),
        )
      }
    }
  }
}

@Preview
@Composable
fun BanHistoryScreenPreview() {
  SuwikiTheme {
    BanHistoryScreen()
  }
}
