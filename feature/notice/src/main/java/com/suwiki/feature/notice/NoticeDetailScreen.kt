package com.suwiki.feature.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun NoticeDetailRoute(
  padding: PaddingValues,
//  viewModel: NoticeDetailViewModel = hiltViewModel(),
  navigateBack: () -> Unit = {},
) {
//  val uiState = viewModel.collectAsState().value
//  viewModel.collectSideEffect { sideEffect ->
//    when (sideEffect) {
//      NoticeDetailSideEffect.NavigateBack -> navigateBack()
//    }
//  }
//
//  LaunchedEffect(key1 = viewModel) {
//    viewModel.checkNoticeDetailLoaded()
//  }
//
//  NoticeDetailScreen(
//    padding = padding,
//    uiState = uiState,
//    title = "asd",
//    date = "2030.03.12",
//    content = "adsasdasd\nsadasd\nasd\n\nasdasd",
//    navigateBack = { viewModel.navigateNoticeDetail() }
//  )
  NoticeDetailScreen(
    padding = padding,
    uiState = NoticeDetailState(),
    title = "asd",
    date = "2030.03.12",
    content = "adsasdasd\nsadasd\nasd\n\nasdasd",
    navigateBack = navigateBack
  )
}

@Composable
fun NoticeDetailScreen(
  padding: PaddingValues,
  uiState: NoticeDetailState, // TODO(progress bar visible에 사용할 예정)
  title: String,
  date: String,
  content: String,
  navigateBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      NoticeDetailScreenAppBar()
    },
    content = { appBarPadding ->
      Column(
        modifier = Modifier
          .padding(appBarPadding)
          .background(White)
          .fillMaxSize()
      ) {
        NoticeDetailTitleContainer(
          modifier = Modifier
            .drawBehind {
              drawLine(
                color = GrayF6,
                Offset(0f, size.height),
                Offset(size.width, size.height),
                strokeWidth = 4.dp.toPx()
              )
            },
          title = title,
          date = date,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
          modifier = Modifier
            .padding(24.dp),
          text = content,
          style = SuwikiTheme.typography.body7
        )
      }
    },
  )
}

@Composable
private fun NoticeDetailScreenAppBar(
  onClickBack: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(White)
      .padding(top = 15.dp, bottom = 15.dp, start = 18.dp),
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_appbar_arrow_left),
      contentDescription = "",
      tint = Gray95,
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickBack)
        .padding(vertical = 2.dp, horizontal = 6.5.dp)
        .align(Alignment.CenterStart),
    )
  }
}

@Composable
private fun NoticeDetailTitleContainer(
  modifier: Modifier = Modifier,
  title: String = "",
  date: String = "",
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(24.dp, 15.dp),
  ) {
    Text(
      text = title,
      style = SuwikiTheme.typography.header6,
      color = Black,
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = date,
      style = SuwikiTheme.typography.caption6,
      color = Gray95,
    )
  }
}

@Preview
@Composable
fun NoticeDetailScreenPreview() {
  SuwikiTheme {
//    NoticeDetailScreen(
//      title = "2023년 04월 17일 데이터베이스 문제",
//      date = "2023.04.17",
//      content = "데이터 베이스의 불문명현 원인으로 인해 특정 되돌리는 풀백을 수행했습니다. \n" +
//        "\n" +
//        "이로 인해 회원가입을 진행해주셨으나 회원가입 처리가 \n" +
//        "되어있지 않는 현상 및 \n" +
//        "강의평가 시험정보 작성을 하였으나 등록되지 않는 \n" +
//        "경우가 발생할 수 있습니다\n" +
//        "\n" +
//        "양해부탁드립니다. \n" +
//        "\n" +
//        "감사합니다.",
//    )
  }
}
