package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiBoardContainer(
  modifier: Modifier = Modifier,
  titleText: String,
  dateText: String,
  onClick: () -> Unit = {},
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .suwikiClickable(onClick = onClick)
      .background(White)
      .padding(24.dp, 15.dp),
  ) {
    Text(
      text = titleText,
      style = SuwikiTheme.typography.header6,
      color = Black,
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = dateText,
      style = SuwikiTheme.typography.caption6,
      color = Gray95,
    )
  }
}

@Composable
@Preview
fun SuwikiNoticeContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiBoardContainer(
        titleText = "Title",
        dateText = "date",
        onClick = {},
      )
    }
  }
}
