package com.suwiki.core.designsystem.component.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiToastView(
  messageText: String,
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier
        .wrapContentSize()
        .background(
          color = Gray95,
          shape = RoundedCornerShape(25.dp),
        ),
      color = Color.Transparent,
    ) {
      Text(
        text = messageText,
        textAlign = TextAlign.Center,
        style = SuwikiTheme.typography.body5,
        color = White,
        modifier = Modifier.padding(16.dp, 10.dp),
      )
    }
  }
}

@Preview
@Composable
fun SetViewPreview() {
  SuwikiTheme {
    SuwikiToastView(
      messageText = "text",
    )
  }
}
