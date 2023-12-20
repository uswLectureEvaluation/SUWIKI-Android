package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.badge.SuwikiGrayLabel
import com.suwiki.core.designsystem.component.button.SuwikiContainedGreyButtonSmall
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiReviewEditContainer(
  modifier: Modifier = Modifier,
  semesterText: String,
  classNameText: String,
  buttonText: String,
  onClick: () -> Unit = {},
) {
  Surface(
    modifier = modifier
      .fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = classNameText,
        style = SuwikiTheme.typography.header6,
        color = Black,
      )
      Spacer(modifier = Modifier.width(6.dp))
      SuwikiGrayLabel(text = semesterText)
      Spacer(modifier = Modifier.weight(1f))
      SuwikiContainedGreyButtonSmall(
        text = buttonText,
        onClick = onClick,
      )
    }
  }
}

@Composable
@Preview
fun SuwikiReviewEditContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiReviewEditContainer(
        semesterText = "학기",
        classNameText = "과목명",
        buttonText = "수정",
        onClick = {},
      )
    }
  }
}
