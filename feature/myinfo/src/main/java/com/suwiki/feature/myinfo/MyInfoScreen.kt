package com.suwiki.feature.myinfo

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiMenuItem
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.shadow.cardShadow
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.findActivity
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.core.ui.util.ASK_SITE
import com.suwiki.core.ui.util.FEEDBACK_SITE
import com.suwiki.core.ui.util.PRIVACY_POLICY_SITE
import com.suwiki.core.ui.util.TERMS_SITE
import okhttp3.internal.immutableListOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

data class MyInfoListItem(
  val title: String,
  val onClick: () -> Unit,
)

@Composable
fun MyInfoRoute(
  padding: PaddingValues,
  viewModel: MyInfoViewModel = hiltViewModel(),
  navigateNotice: () -> Unit,
  navigateMyEvaluation: () -> Unit,
  navigateMyAccount: () -> Unit,
  navigateMyPoint: () -> Unit,
  navigateBanHistory: () -> Unit,
  navigateLogin: () -> Unit,
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit = {},
) {
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value

  val uriHandler = LocalUriHandler.current
  val context = LocalContext.current

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is MyInfoSideEffect.NavigateNotice -> navigateNotice()
      is MyInfoSideEffect.NavigateMyEvaluation -> navigateMyEvaluation()
      is MyInfoSideEffect.NavigateMyAccount -> navigateMyAccount()
      is MyInfoSideEffect.NavigateMyPoint -> navigateMyPoint()
      is MyInfoSideEffect.NavigateBanHistory -> navigateBanHistory()
      MyInfoSideEffect.ShowNeedLoginToast -> onShowToast(context.getString(R.string.my_info_screen_need_login_toast))
      MyInfoSideEffect.ShowOpenLicenses -> context.startActivity(
        Intent(
          context.findActivity(),
          OssLicensesMenuActivity::class.java,
        ),
      )

      is MyInfoSideEffect.HandleException -> handleException(sideEffect.throwable)
      MyInfoSideEffect.OpenPersonalPolicyWebSite -> uriHandler.openUri(PRIVACY_POLICY_SITE)
      MyInfoSideEffect.OpenTermWebSite -> uriHandler.openUri(TERMS_SITE)
      MyInfoSideEffect.OpenAskWebSite -> uriHandler.openUri(ASK_SITE)
      MyInfoSideEffect.OpenFeedbackWebSite -> uriHandler.openUri(FEEDBACK_SITE)
      MyInfoSideEffect.NavigateLogin -> navigateLogin()
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
    onClickMyEvaluationButton = viewModel::navigateMyEvaluation,
    onClickMyAccountButton = viewModel::navigateMyAccount,
    onClickMyPointItem = viewModel::navigateMyPoint,
    onClickBanHistoryItem = viewModel::navigateBanHistory,
    onClickAskButton = viewModel::openAskSite,
    onClickSendFeedback = viewModel::openFeedbackSite,
    onClickTerm = viewModel::openTermSite,
    onClickPersonalInfoPolicy = viewModel::openPersonalPolicySite,
    onClickOpenLicense = viewModel::showOpenLicense,
    onClickLogin = viewModel::navigateLogin,
  )
}

@Composable
fun MyInfoScreen(
  padding: PaddingValues,
  uiState: MyInfoState,
  scrollState: ScrollState,
  onClickNoticeButton: () -> Unit,
  onClickMyEvaluationButton: () -> Unit,
  onClickMyAccountButton: () -> Unit,
  onClickMyPointItem: () -> Unit,
  onClickBanHistoryItem: () -> Unit,
  onClickAskButton: () -> Unit,
  onClickSendFeedback: () -> Unit,
  onClickTerm: () -> Unit,
  onClickPersonalInfoPolicy: () -> Unit,
  onClickOpenLicense: () -> Unit,
  onClickLogin: () -> Unit,
) {
  val myList = immutableListOf(
    MyInfoListItem(
      title = stringResource(R.string.my_info_point),
      onClick = onClickMyPointItem,
    ),
    MyInfoListItem(
      title = stringResource(R.string.my_info_ban_history),
      onClick = onClickBanHistoryItem,
    ),
  )
  val serviceList = immutableListOf(
    MyInfoListItem(
      title = stringResource(R.string.my_info_send_feedback),
      onClick = onClickSendFeedback,
    ),
    MyInfoListItem(
      title = stringResource(R.string.my_info_use_terms),
      onClick = onClickTerm,
    ),
    MyInfoListItem(
      title = stringResource(R.string.my_info_privacy_policy),
      onClick = onClickPersonalInfoPolicy,
    ),
    MyInfoListItem(
      title = stringResource(R.string.my_info_open_source_library),
      onClick = onClickOpenLicense,
    ),
  )
  Column(
    modifier = Modifier
      .padding(padding)
      .verticalScroll(scrollState),
  ) {
    Box(
      modifier = Modifier.background(if (uiState.showMyInfoCard) GrayF6 else White),
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        with(uiState) {
          if (showMyInfoCard) {
            LoginMyInfoCard(
              userId = loginId,
              point = point,
              onClickMyEvaluation = onClickMyEvaluationButton,
            )
          } else {
            LogoutMyInfoCard(onClickLogin)
          }
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
            onClickItem = onClickAskButton,
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
            onClickItem = onClickMyAccountButton,
          )
        }
      }
    }
    Column(
      modifier = Modifier
        .background(White)
        .fillMaxSize(),
    ) {
      if (uiState.showMyInfoCard) {
        SuwikiMenuItem(title = stringResource(R.string.my_info_my))

        myList.forEach { (title, onClick) ->
          MyInfoListItemContainer(
            title = title,
            onClick = onClick,
          )
        }
      }
      SuwikiMenuItem(title = stringResource(R.string.my_info_service))

      serviceList.forEach { (title, onClick)->
        MyInfoListItemContainer(
          title = title,
          onClick = onClick,
        )
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
  userId: String,
  point: Int,
  onClickMyEvaluation: () -> Unit = {},
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
        text = userId,
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
          text = point.toString(),
          style = SuwikiTheme.typography.body4,
          color = Black,
        )
      }
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(top = 31.dp, end = 6.dp)
        .suwikiClickable(onClick = onClickMyEvaluation),
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
private fun MyInfoListItemContainer(
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
      onClickMyEvaluationButton = {},
      onClickMyAccountButton = {},
      onClickMyPointItem = {},
      onClickBanHistoryItem = {},
      onClickSendFeedback = {},
      onClickTerm = {},
      onClickPersonalInfoPolicy = {},
      onClickOpenLicense = {},
      onClickAskButton = {},
      onClickLogin = {},
      )
  }
}
