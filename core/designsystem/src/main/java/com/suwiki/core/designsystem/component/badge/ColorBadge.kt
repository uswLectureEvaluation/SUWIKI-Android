package com.suwiki.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Blue10
import com.suwiki.core.designsystem.theme.Blue100
import com.suwiki.core.designsystem.theme.Green10
import com.suwiki.core.designsystem.theme.Green100
import com.suwiki.core.designsystem.theme.Orange10
import com.suwiki.core.designsystem.theme.Orange100
import com.suwiki.core.designsystem.theme.SuwikiTheme

enum class BadgeType {
  ORANGE,
  BLUE,
  GREEN,
}

@Composable
fun SuwikiColorBadge(
  modifier: Modifier = Modifier,
  type: BadgeType,
  text: String,
  englishText: String,
) {
  val (backgroundColor, contentColor) = when (type) {
    BadgeType.ORANGE -> Orange10 to Orange100
    BadgeType.BLUE -> Blue10 to Blue100
    BadgeType.GREEN -> Green10 to Green100
  }
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(5.dp))
      .background(color = backgroundColor)
      .wrapContentHeight()
      .padding(vertical = 4.dp, horizontal = 6.dp),
  ) {
    Row(
      modifier = Modifier.wrapContentWidth(),
    ) {
      Text(
        text = text,
        style = SuwikiTheme.typography.caption2,
        color = contentColor,
      )
      Spacer(modifier = Modifier.width(2.dp))
      Text(
        text = englishText,
        style = SuwikiTheme.typography.caption1,
        color = contentColor,
      )
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiColorBadgePreview() {
  SuwikiTheme {
    Column {
      SuwikiColorBadge(
        type = BadgeType.GREEN,
        text = "학점",
        englishText = "label",
      )
      SuwikiColorBadge(
        type = BadgeType.BLUE,
        text = "학점",
        englishText = "label",
      )
      SuwikiColorBadge(
        type = BadgeType.ORANGE,
        text = "학점",
        englishText = "label",
      )
    }
  }
}
