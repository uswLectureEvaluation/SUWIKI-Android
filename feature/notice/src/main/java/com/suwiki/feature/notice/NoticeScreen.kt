package com.suwiki.feature.notice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.container.SuwikiNoticeContainer
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import okhttp3.internal.immutableListOf

@Composable
fun NoticeRoute(
  padding: PaddingValues,
//  viewModel: NoticeViewModel = hiltViewModel(),
  navigateNoticeDetail: () -> Unit,
) {
//  val uiState = viewModel.collectAsState().value
//  viewModel.collectSideEffect { sideEffect ->
//    when (sideEffect) {
//      NoticeSideEffect.NavigateNoticeDetail -> navigateNoticeDetail()
//    }
//  }
//
//  LaunchedEffect(key1 = viewModel) {
//    viewModel.checkNoticeListLoaded()
//  }
//
//  NoticeScreen(
//    padding = padding,
//    uiState = uiState,
//    navigateNoticeDetail = { viewModel.navigateNoticeDetail() },
//  )

  NoticeScreen(
    padding = padding,
    uiState = NoticeState(),
    navigateNoticeDetail = navigateNoticeDetail,
  )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoticeScreen(
  padding: PaddingValues,
  uiState: NoticeState, // TODO(progress bar visible에 사용할 예정)
  navigateNoticeDetail: () -> Unit,
) {
  // TODO REMOVE
  val sampleNoticeTitle = immutableListOf(
    "회원가입 필독",
    "2023년 04월 17일 데이터베이스 문제",
    "개인정보 처리방침 개정 안내",
    "아이폰 유저 시간표 반영 주의사항"
  )
  Scaffold(
    topBar = { NoticeScreenAppBar(title = stringResource(R.string.notice)) },
    content = { appBarPadding ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(appBarPadding)
          .background(White)
      ) {
        items(items = sampleNoticeTitle) { title ->
          SuwikiNoticeContainer(
            titleText = title,
            dateText = "2023.04.17",
            onClick = navigateNoticeDetail,
          )
        }
      }
    },
  )
}

@Composable
private fun NoticeScreenAppBar(
  title: String = "",
  onClickBack: () -> Unit = {},
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(White)
      .padding(top = 15.dp, bottom = 15.dp, start = 18.dp, end = 24.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_appbar_arrow_left),
      contentDescription = "",
      tint = Gray95,
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickBack)
        .padding(vertical = 2.dp, horizontal = 6.5.dp),
    )
    Text(
      text = title,
      style = SuwikiTheme.typography.header6,
    )
    Spacer(modifier = Modifier.size(24.dp))
  }
}

@Preview
@Composable
fun NoticeScreenPreview() {
  SuwikiTheme {
//    NoticeScreen(padding = PaddingValues(0.dp))
  }
}
