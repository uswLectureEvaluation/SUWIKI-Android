package com.suwiki.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiBasicLabel(
  modifier: Modifier = Modifier,
  text: String,
  textColor: Color,
  backgroundColor: Color,
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(color = backgroundColor)
      .wrapContentHeight()
      .padding(vertical = 2.dp, horizontal = 6.dp),
  ) {
    Text(
      text = text,
      style = SuwikiTheme.typography.caption4,
      color = textColor,
      modifier = Modifier.align(Alignment.Center),
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun BadgePreview() {
  SuwikiTheme {
    SuwikiBasicLabel(
      text = "label",
      textColor = White,
      backgroundColor = Primary,
    )
  }
}
