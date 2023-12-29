package com.suwiki.feature.myinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiMenuItem
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.shadow.cardShadow
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import okhttp3.internal.immutableListOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyInfoRoute(
  padding: PaddingValues,
  viewModel: MyInfoViewModel = hiltViewModel(),
  navigateNotice: () -> Unit,
) {
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyInfoSideEffect.NavigateNotice -> navigateNotice()
    }
  }

  LaunchedEffect(key1 = viewModel) {
    viewModel.checkLoggedIn()
  }

  MyInfoScreen(
    padding = padding,
    uiState = uiState,
    scrollState = scrollState,
    onClickNoticeButton = viewModel::navigateNotice,
  )
}

@Composable
fun MyInfoScreen(
  padding: PaddingValues,
  uiState: MyInfoState,
  scrollState: ScrollState,
  onClickNoticeButton: () -> Unit,
) {
  val myList = immutableListOf(
    R.string.my_info_point,
    R.string.my_info_ban_history,
  )
  val serviceList = immutableListOf(
    R.string.my_info_send_feedback,
    R.string.my_info_use_terms,
    R.string.my_info_privacy_policy,
    R.string.my_info_open_source_library,
  )
  Column(
    modifier = Modifier.padding(padding),
  ) {
    Box(
      modifier = Modifier.background(if (uiState.showMyInfoCard) GrayF6 else White),
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (uiState.showMyInfoCard) {
          LoginMyInfoCard()
        } else {
          LogoutMyInfoCard()
        }
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .padding(vertical = 28.dp, horizontal = 41.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        ) {
          MyInfoMenuItem(
            title = stringResource(R.string.my_info_notice),
            iconId = R.drawable.ic_my_info_notice,
            onClickItem = onClickNoticeButton,
          )
          VerticalDivider(
            modifier = Modifier
              .padding(vertical = 5.dp),
            thickness = 1.dp,
            color = GrayF6,
          )
          MyInfoMenuItem(
            title = stringResource(R.string.my_info_contact),
            iconId = R.drawable.ic_my_info_comment,
          )
          VerticalDivider(
            modifier = Modifier
              .padding(vertical = 5.dp),
            thickness = 1.dp,
            color = GrayF6,
          )
          MyInfoMenuItem(
            title = stringResource(R.string.my_info_account_manage),
            iconId = R.drawable.ic_my_info_setting,
          )
        }
      }
    }
    Column(
      modifier = Modifier
        .background(White)
        .fillMaxSize()
        .verticalScroll(scrollState),
    ) {
      if (uiState.showMyInfoCard) {
        SuwikiMenuItem(title = stringResource(R.string.my_info_my))

        myList.forEach { title ->
          MyInfoListItem(title = stringResource(title))
        }
      }
      SuwikiMenuItem(title = stringResource(R.string.my_info_service))

      serviceList.forEach { title ->
        MyInfoListItem(title = stringResource(title))
      }
    }
    if (uiState.isLoading) {
      LoadingScreen()
    }
  }
}

@Composable
fun LogoutMyInfoCard(
  onClickLogin: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 52.dp, start = 24.dp, end = 24.dp)
      .background(
        color = GrayF6,
        shape = RoundedCornerShape(10.dp),
      ),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(top = 19.dp, start = 16.dp)
        .suwikiClickable(onClick = onClickLogin),
    ) {
      Text(
        text = stringResource(R.string.my_info_login),
        style = SuwikiTheme.typography.header2,
        color = Black,
      )
      Image(
        painter = painterResource(id = R.drawable.ic_arrow_gray_right),
        contentDescription = "",
      )
    }
    Text(
      modifier = Modifier.padding(start = 16.dp, bottom = 19.dp),
      text = stringResource(R.string.my_info_check_mine),
      style = SuwikiTheme.typography.body4,
      color = Gray95,
    )
  }
}

@Composable
fun LoginMyInfoCard(
  onClickMyPost: () -> Unit = {},
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 52.dp, start = 24.dp, end = 24.dp)
      .cardShadow()
      .background(
        color = White,
        shape = RoundedCornerShape(10.dp),
      ),
  ) {
    Column {
      Text(
        modifier = Modifier.padding(top = 19.dp, start = 16.dp),
        text = stringResource(R.string.my_info_test_nickname),
        style = SuwikiTheme.typography.header2,
        color = Black,
      )
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp, bottom = 19.dp),
      ) {
        Image(
          modifier = Modifier
            .padding(end = 4.dp),
          painter = painterResource(id = R.drawable.ic_my_info_point),
          contentDescription = "",
        )
        Text(
          text = stringResource(R.string.my_info_test_point),
          style = SuwikiTheme.typography.body4,
          color = Black,
        )
      }
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(top = 31.dp, end = 6.dp)
        .suwikiClickable(onClick = onClickMyPost),
    ) {
      Text(
        text = stringResource(R.string.my_info_my_post),
        style = SuwikiTheme.typography.caption1,
        color = Black,
      )
      Image(
        painter = painterResource(id = R.drawable.ic_arrow_gray_right),
        contentDescription = "",
      )
    }
  }
}

@Composable
private fun MyInfoMenuItem(
  title: String = "",
  iconId: Int,
  onClickItem: () -> Unit = {},
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .suwikiClickable(onClick = onClickItem),
  ) {
    Image(
      painter = painterResource(id = iconId),
      contentDescription = "",
    )
    Text(
      text = title,
      style = SuwikiTheme.typography.caption2,
    )
  }
}

@Composable
private fun MyInfoListItem(
  title: String = "",
  onClick: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .background(White)
      .fillMaxWidth()
      .wrapContentHeight()
      .suwikiClickable(
        onClick = onClick,
        rippleColor = Gray6A,
      ),
  ) {
    Text(
      text = title,
      style = SuwikiTheme.typography.body2,
      modifier = Modifier
        .align(Alignment.CenterStart)
        .padding(top = 13.dp, bottom = 14.dp, start = 24.dp, end = 52.dp),
    )
  }
}

@Preview(showSystemUi = true)
@Composable
fun MyInfoScreenScreenPreview() {
  val scrollState = rememberScrollState()

  SuwikiTheme {
    MyInfoScreen(
      padding = PaddingValues(0.dp),
      uiState = MyInfoState(true),
      scrollState = scrollState,
      onClickNoticeButton = {},
    )
  }
}
