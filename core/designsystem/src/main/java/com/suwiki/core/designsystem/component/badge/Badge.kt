package com.suwiki.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

enum class BadgeColor(
  val textColor: Color,
  val backgroundColor: Color,
) {
  Gray(
    textColor = Gray6A,
    backgroundColor = GrayF6,
  ),
  Blue(
    textColor = White,
    backgroundColor = Primary
  )
}

@Composable
fun SuwikiBadge(
  modifier: Modifier = Modifier,
  text: String = "",
  color: BadgeColor,
) {
  with(color) {
    Box(
      modifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .background(color = backgroundColor)
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
}

@Preview
@Composable
fun BadgePreview() {
  SuwikiTheme {
    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiBadge(text = "label", color = BadgeColor.Blue)
      SuwikiBadge(text = "label", color = BadgeColor.Gray)
    }
  }
}
