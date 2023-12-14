package com.suwiki.core.designsystem.component.tapbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiTabBarActiveItem(
  modifier: Modifier = Modifier,
  title: String = "",
  onClickItem: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .drawBehind {
        val borderWidth = 2.dp.toPx()
        val y = size.height - borderWidth / 2
        drawLine(
          color = Black,
          start = Offset(0f, y),
          end = Offset(size.width, y),
          strokeWidth = borderWidth
        )
      }
      .suwikiClickable(onClick = onClickItem)
      .padding(vertical = 12.dp)
      .wrapContentSize(),
  ) {
    Text(
      text = title,
      style = SuwikiTheme.typography.header6,
    )
  }
}

@Composable
fun SuwikiTabBarInActiveItem(
  modifier: Modifier = Modifier,
  title: String = "",
  onClickItem: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .suwikiClickable(onClick = onClickItem)
      .padding(vertical = 12.dp)
      .wrapContentSize(),
  ) {
    Text(
      text = title,
      style = SuwikiTheme.typography.header6,
      color = Gray95,
    )
  }
}

@Preview(widthDp = 200)
@Composable
fun SuwikiTabBarItemPreview() {
  SuwikiTheme {
    Row(
      horizontalArrangement = Arrangement.Start,
    ) {
      SuwikiTabBarActiveItem(
        title = "메뉴0",
        onClickItem = { /*TODO*/ },
      )
      Spacer(modifier = Modifier.width(10.dp))
      SuwikiTabBarInActiveItem(
        title = "메뉴0",
        onClickItem = { /*TODO*/ },
      )
    }
  }
}
