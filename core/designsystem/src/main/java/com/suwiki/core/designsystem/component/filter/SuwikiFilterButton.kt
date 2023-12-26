package com.suwiki.core.designsystem.component.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiFilterButton(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .suwikiClickable(onClick = onClick)
      .wrapContentHeight()
      .padding(vertical = 4.dp, horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = text,
      style = SuwikiTheme.typography.header6,
      color = Primary,
    )
    Icon(
      painter = painterResource(id = R.drawable.ic_filter_arrow_down),
      contentDescription = "",
      tint = Primary,
      modifier = Modifier
        .size(24.dp)
        .padding(vertical = 9.dp, horizontal = 7.dp),
    )
  }
}

@Preview(widthDp = 400, heightDp = 50)
@Composable
fun SuwikiFilterButtonPreview() {
  SuwikiTheme {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      SuwikiFilterButton(text = "학과필터")
    }
  }
}
