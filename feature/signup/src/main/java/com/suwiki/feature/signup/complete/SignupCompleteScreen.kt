package com.suwiki.feature.SignupComplete.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.button.SuwikiOutlinedButton
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.util.SCHOOL_HOMEPAGE
import com.suwiki.feature.signup.R
import com.suwiki.feature.signup.complete.SignupCompleteSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignupCompleteRoute(
  viewModel: SignupCompleteViewModel = hiltViewModel(),
  navigateLogin: () -> Unit,
) {
  val uriHandler = LocalUriHandler.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      SignupCompleteSideEffect.NavigateLogin -> navigateLogin()
      SignupCompleteSideEffect.OpenSchoolWebSite -> uriHandler.openUri(SCHOOL_HOMEPAGE)
    }
  }

  SignupCompleteScreen(
    onClickConfirmEmailButton = viewModel::openSchoolWebsite,
    onClickLoginButton = viewModel::navigateLogin,
  )
}

@Composable
fun SignupCompleteScreen(
  onClickConfirmEmailButton: () -> Unit = {},
  onClickLoginButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .padding(
        top = 63.dp,
        start = 24.dp,
        end = 24.dp,
        bottom = 107.dp,
      )
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "메일로 인증 링크를\n보냈습니다!",
      textAlign = TextAlign.Center,
      style = SuwikiTheme.typography.header1,
    )

    Spacer(modifier = Modifier.size(34.dp))

    Image(
      modifier = Modifier.size(180.dp),
      painter = painterResource(id = R.drawable.ic_signup_complete),
      contentDescription = null,
    )

    Spacer(modifier = Modifier.size(22.dp))

    Text(text = "가입 완료를 위해 이메일을 확인하세요", style = SuwikiTheme.typography.header2)

    Spacer(modifier = Modifier.size(6.dp))

    Text(
      text = "인증 링크를 확인하지 않으면\n서비스 이용이 불가능합니다!",
      style = SuwikiTheme.typography.header7,
      color = Gray6A,
      textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.weight(1f))

    SuwikiContainedLargeButton(text = "이메일 확인하기", onClick = onClickConfirmEmailButton)

    Spacer(modifier = Modifier.size(12.dp))

    SuwikiOutlinedButton(text = "로그인", onClick = onClickLoginButton)
  }
}

@Preview(showBackground = true)
@Composable
fun SignupCompleteScreenPreview() {
  SuwikiTheme {
    SignupCompleteScreen()
  }
}
