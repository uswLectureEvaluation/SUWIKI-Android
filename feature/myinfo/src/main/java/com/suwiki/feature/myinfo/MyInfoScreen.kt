package com.suwiki.feature.myinfo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun MyInfoScreen(
  padding: PaddingValues,
) {
  Text(
    modifier = Modifier.padding(padding),
    text = "내 정보",
  )
}

@Preview
@Composable
fun MyInfoScreenScreenPreview() {
  SuwikiTheme {
    MyInfoScreen(padding = PaddingValues(0.dp))
  }
}
