package com.suwiki.core.designsystem.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayFB
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiTimeTableAppBar(
  modifier: Modifier = Modifier,
  timeTableName: String,
  onClickPlus: () -> Unit = {},
  onClickList: () -> Unit = {},
  onClickSetting: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(GrayFB)
  ) {
    Text(
      text = timeTableName,
      style = SuwikiTheme.typography.header1,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
        .padding(top = 28.dp, bottom = 12.dp, start = 24.dp, end = 130.dp)
    )
    Row(
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(top = 37.dp, bottom = 12.dp, end = 20.dp)
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_appbar_add),
        contentDescription = "",
        tint = Gray95,
        modifier = Modifier
          .size(24.dp)
          .suwikiClickable(onClick = onClickPlus)
          .padding(5.dp),
      )
      Spacer(modifier = Modifier.width(18.dp))
      Icon(
        painter = painterResource(id = R.drawable.ic_appbar_list),
        contentDescription = "",
        tint = Gray95,
        modifier = Modifier
          .size(24.dp)
          .suwikiClickable(onClick = onClickList)
          .padding(vertical = 6.dp, horizontal = 4.dp),
      )
      Spacer(modifier = Modifier.width(18.dp))
      Icon(
        painter = painterResource(id = R.drawable.ic_appbar_setting),
        contentDescription = "",
        tint = Gray95,
        modifier = Modifier
          .size(24.dp)
          .suwikiClickable(onClick = onClickSetting)
          .padding(4.dp),
      )
    }
  }
}

@Preview
@Composable
fun SuwikiTimeTableAppBarPreview() {
  SuwikiTheme {
    SuwikiTimeTableAppBar(
      timeTableName = "재밌는시간표",
      onClickPlus = { /*TODO*/ },
      onClickList = { /*TODO*/ },
      onClickSetting = { /*TODO*/ },
    )
  }
}
