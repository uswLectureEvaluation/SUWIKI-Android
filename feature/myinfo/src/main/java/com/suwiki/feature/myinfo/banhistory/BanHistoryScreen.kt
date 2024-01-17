package com.suwiki.feature.myinfo.banhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.container.SuwikiNoticeContainer
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.user.Suspension
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDateTime

val sampleBanHistory = persistentListOf(
  Suspension.Ban(
    reason = "사유",
    judgement = "5일 정지",
    createdAt = LocalDateTime.now(),
    expiredAt = LocalDateTime.now()
  ),
  Suspension.Ban(
    reason = "사유",
    judgement = "5일 정지",
    createdAt = LocalDateTime.now(),
    expiredAt = LocalDateTime.now()
  ),
  Suspension.Ban(
    reason = "사유",
    judgement = "5일 정지",
    createdAt = LocalDateTime.now(),
    expiredAt = LocalDateTime.now()
  ),
  Suspension.Ban(
    reason = "사유",
    judgement = "5일 정지",
    createdAt = LocalDateTime.now(),
    expiredAt = LocalDateTime.now()
  ),
)

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
      .background(White)
  ) {
    SuwikiAppBarWithTitle(
      title = "이용제한 내역",
      showBackIcon = true,
      showCloseIcon = false,
      onClickBack = popBackStack,
    )
    LazyColumn {
//      items(items = uiState.banHistory) { banHistory ->
      items(items = sampleBanHistory) { banHistory ->
        SuwikiNoticeContainer(
          titleText = banHistory.reason,
          dateText = banHistory.createdAt.toString(),
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
