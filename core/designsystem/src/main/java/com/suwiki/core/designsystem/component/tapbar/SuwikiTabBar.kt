package com.suwiki.core.designsystem.component.tapbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.SuwikiTheme

enum class LeftRightIndicate {
  LEFT,
  RIGHT,
}

@Composable
fun SuwikiTabBar(
  modifier: Modifier = Modifier,
  leftContent: @Composable () -> Unit = @Composable {},
  rightContent: @Composable () -> Unit = @Composable {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
  ) {
    Row(
      modifier = Modifier
        .padding(start = 24.dp, top = 6.dp),
    ) {
      SuwikiTabBarLeftItem(leftContent)
      Spacer(modifier = Modifier.width(12.dp))
      SuwikiTabBarRightItem(rightContent)
    }
  }
}

@Composable
fun SuwikiTabBarLeftItem(
  content: @Composable () -> Unit = @Composable {},
) {
  content()
}

@Composable
fun SuwikiTabBarRightItem(
  content: @Composable () -> Unit = @Composable {},
) {
  content()
}

@Preview(widthDp = 300)
@Composable
fun SuwikiTabBarPreview() {
  var isLeftActive by remember { mutableStateOf(true) }
  var isRightActive by remember { mutableStateOf(false) }

  val changeActive = {
    isLeftActive = !isLeftActive
    isRightActive = !isRightActive
  }

  SuwikiTheme {
    Column {
      SuwikiTabBar(
        leftContent = {
          SetTabItemActive(
            isLeftActive = isLeftActive,
            isRightActive = isRightActive,
            isHasCount = true,
            clickIndicator = LeftRightIndicate.LEFT,
            itemContentCnt = 0,
            title = "메뉴",
            changeActive = changeActive,
          )
        },
        rightContent = {
          SetTabItemActive(
            isLeftActive = isLeftActive,
            isRightActive = isRightActive,
            isHasCount = false,
            clickIndicator = LeftRightIndicate.RIGHT,
            itemContentCnt = 0,
            title = "메뉴",
            changeActive = changeActive,
          )
        },
      )
      if (isLeftActive) {
        SampleAScreen()
      } else {
        SampleBScreen()
      }
    }
  }
}

@Composable
fun SetTabItemActive(
  isLeftActive: Boolean,
  isRightActive: Boolean,
  isHasCount: Boolean,
  clickIndicator: LeftRightIndicate,
  itemContentCnt: Int,
  title: String,
  changeActive: () -> Unit = {},
) {
  val isActive =
    if (clickIndicator == LeftRightIndicate.LEFT) isLeftActive else isRightActive

  if (isActive) {
    SuwikiTabBarActiveItem(
      title = if (isHasCount) "$title($itemContentCnt)" else title,
      onClickItem = changeActive,
    )
  } else {
    SuwikiTabBarInActiveItem(
      title = if (isHasCount) "$title($itemContentCnt)" else title,
      onClickItem = changeActive,
    )
  }
}

// 화면 전환 테스트용
@Composable
fun SampleAScreen() {
  Text(text = "A")
}

@Composable
fun SampleBScreen() {
  Text(text = "B")
}
