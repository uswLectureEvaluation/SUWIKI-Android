package com.suwiki.core.designsystem.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiAppBar(
  modifier: Modifier = Modifier,
  title: String = "",
  onClickBack: () -> Unit = {},
  onClickRemove: () -> Unit = {},
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(White)
      .padding(vertical = 15.dp, horizontal = 24.dp)
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_appbar_arrow_left),
      contentDescription = "",
      tint = Gray95,
      modifier = Modifier
        .size(24.dp)
        .suwikiClickable(onClick = onClickBack)
        .padding(top = 3.dp, bottom = 2.dp, end = 13.dp)
    )
    Text(
      text = title,
      style = SuwikiTheme.typography.header6
    )
    Icon(
      painter = painterResource(id = R.drawable.ic_appbar_x_mark),
      contentDescription = "",
      tint = Gray95,
      modifier = Modifier
        .size(24.dp)
        .suwikiClickable(onClick = onClickRemove)
        .padding(3.dp)
    )
  }
}

@Preview
@Composable
fun SuwikiAppBarPreview() {
  SuwikiTheme {
    SuwikiAppBar(
      title = "타이틀",
      onClickBack = { /*TODO*/ },
      onClickRemove = { /*TODO*/ },
    )
  }
}
