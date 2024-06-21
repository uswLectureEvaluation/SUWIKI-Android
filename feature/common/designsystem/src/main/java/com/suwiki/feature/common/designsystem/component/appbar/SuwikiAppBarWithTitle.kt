package com.suwiki.feature.common.designsystem.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.feature.common.designsystem.R
import com.suwiki.feature.common.designsystem.theme.Gray95
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.extension.suwikiClickable

@Composable
fun SuwikiAppBarWithTitle(
  modifier: Modifier = Modifier,
  title: String? = null,
  showCloseIcon: Boolean = true,
  showBackIcon: Boolean = true,
  onClickBack: () -> Unit = {},
  onClickClose: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(White)
      .padding(top = 15.dp, bottom = 15.dp, start = 18.dp, end = 24.dp),
  ) {
    if (showBackIcon) {
      Icon(
        modifier = Modifier
          .align(Alignment.CenterStart)
          .size(24.dp)
          .clip(CircleShape)
          .suwikiClickable(onClick = onClickBack)
          .padding(vertical = 2.dp, horizontal = 6.5.dp),
        painter = painterResource(id = R.drawable.ic_appbar_arrow_left),
        contentDescription = "",
        tint = Gray95,
      )
    }

    if (title != null) {
      Text(
        modifier = Modifier.align(Alignment.Center),
        text = title,
        style = SuwikiTheme.typography.header6,
      )
    }

    if (showCloseIcon) {
      Icon(
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .size(24.dp)
          .clip(CircleShape)
          .suwikiClickable(onClick = onClickClose)
          .padding(3.dp),
        painter = painterResource(id = R.drawable.ic_appbar_close_mark),
        contentDescription = "",
        tint = Gray95,
      )
    }
  }
}

@Preview
@Composable
fun SuwikiAppBarPreview() {
  SuwikiTheme {
    SuwikiAppBarWithTitle(
      title = "타이틀",
      onClickBack = { /*TODO*/ },
      onClickClose = { /*TODO*/ },
    )
  }
}
