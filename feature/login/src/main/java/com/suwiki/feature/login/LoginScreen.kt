package com.suwiki.feature.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun LoginRoute() {
  LoginScreen()
}

@Composable
fun LoginScreen() {
  Text(text = "로그인")
}

@Preview
@Composable
fun LectureEvaluationScreenPreview() {
  SuwikiTheme {
    LoginScreen()
  }
}
