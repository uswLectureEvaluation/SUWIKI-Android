package com.suwiki.feature.notice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.container.SuwikiNoticeContainer
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.notice.Notice
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDateTime

@Composable
fun NoticeRoute(
  padding: PaddingValues,
  viewModel: NoticeViewModel = hiltViewModel(),
  navigateNoticeDetail: () -> Unit,
  popBackStack: () -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      NoticeSideEffect.NavigateNoticeDetail -> navigateNoticeDetail()
      NoticeSideEffect.PopBackStack -> popBackStack()
    }
  }

  LaunchedEffect(key1 = viewModel) {
    viewModel.loadNoticeList()
  }

  NoticeScreen(
    padding = padding,
    noticeList = uiState.noticeList,
    uiState = uiState,
    navigateNoticeDetail = { viewModel.navigateNoticeDetail() },
    popBackStack = viewModel::popBackStack,
  )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoticeScreen(
  padding: PaddingValues,
  noticeList: List<Notice>,
  uiState: NoticeState,
  navigateNoticeDetail: () -> Unit,
  popBackStack: () -> Unit,
) {
  Column(
    modifier = Modifier
      .padding(padding)
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.notice),
      onClickBack = popBackStack,
      showBackIcon = true,
      showCloseIcon = false,
    )
    LazyColumn {
      items(items = noticeList) { notice ->
        SuwikiNoticeContainer(
          titleText = notice.title,
          dateText = notice.date.toString(),
          onClick = navigateNoticeDetail,
        )
      }
    }
    if (uiState.isLoading) {
      LoadingScreen()
    }
  }
}

@Preview
@Composable
fun NoticeScreenPreview() {
  val sampleNoticeList = listOf(
    Notice(id = 1, title = "회원가입 필독", date = LocalDateTime.now()),
    Notice(id = 2, title = "2023년 04월 17일 데이터베이스 문제", date = LocalDateTime.now()),
    Notice(id = 3, title = "개인정보 처리방침 개정 안내", date = LocalDateTime.now()),
    Notice(id = 4, title = "아이폰 유저 시간표 반영 주의사항", date = LocalDateTime.now()),
  )
  SuwikiTheme {
    NoticeScreen(
      padding = PaddingValues(0.dp),
      uiState = NoticeState(false),
      noticeList = sampleNoticeList,
      navigateNoticeDetail = {},
      popBackStack = {},
    )
  }
}
