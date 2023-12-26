package com.suwiki.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun LoginRoute() {
  LoginScreen()
}

@Composable
fun LoginScreen() {
  Box {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = 63.dp, bottom = 71.dp, start = 24.dp, end = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = stringResource(R.string.word_login), style = SuwikiTheme.typography.header1)

      Spacer(modifier = Modifier.size(26.dp))

      SuwikiRegularTextField(
        label = stringResource(R.string.word_id),
        placeholder = stringResource(R.string.login_screen_id_textfield_placeholder),
      )

      Spacer(modifier = Modifier.size(20.dp))

      SuwikiRegularTextField(
        label = stringResource(R.string.word_password),
        placeholder = stringResource(R.string.login_screen_password_textfield_placeholder),
      )

      Spacer(modifier = Modifier.weight(1f))

      Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(text = stringResource(R.string.login_screen_find_id), style = SuwikiTheme.typography.body5, color = Gray6A)
        VerticalDivider(
          modifier = Modifier.padding(vertical = 4.5.dp),
          thickness = 1.dp,
          color = GrayF6,
        )
        Text(text = stringResource(R.string.login_screen_find_password), style = SuwikiTheme.typography.body5, color = Gray6A)
        VerticalDivider(
          modifier = Modifier.padding(vertical = 4.5.dp),
          thickness = 1.dp,
          color = GrayF6,
        )
        Text(text = stringResource(R.string.word_signup), style = SuwikiTheme.typography.body4, color = Primary)
      }

      Spacer(modifier = Modifier.size(20.dp))

      SuwikiContainedLargeButton(text = stringResource(R.string.word_login))
    }

    LoadingScreen()
  }
}

@Preview(showBackground = true)
@Composable
fun LectureEvaluationScreenPreview() {
  SuwikiTheme {
    LoginScreen()
  }
}
