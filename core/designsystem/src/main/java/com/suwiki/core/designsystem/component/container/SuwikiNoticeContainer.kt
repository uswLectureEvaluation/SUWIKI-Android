package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiNoticeContainer(
  modifier: Modifier = Modifier,
  titleText: String,
  dateText: String,
  onClick: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = Primary,
        onClick = onClick,
      ),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
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
  }
}

@Composable
@Preview
fun SuwikiNoticeContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiNoticeContainer(
        titleText = "Title",
        dateText = "date",
        onClick = {},
      )
    }
  }
}
