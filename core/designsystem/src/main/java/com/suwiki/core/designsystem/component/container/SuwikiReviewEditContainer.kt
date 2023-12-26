package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.badge.BadgeColor
import com.suwiki.core.designsystem.component.badge.SuwikiBadge
import com.suwiki.core.designsystem.component.button.SuwikiContainedSmallButton
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiReviewEditContainer(
  modifier: Modifier = Modifier,
  semesterText: String,
  classNameText: String,
  onClickEditButton: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(24.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = classNameText,
      style = SuwikiTheme.typography.header6,
      color = Black,
    )
    Spacer(modifier = Modifier.width(6.dp))
    SuwikiBadge(color = BadgeColor.Gray, text = semesterText)
    Spacer(modifier = Modifier.weight(1f))
    SuwikiContainedSmallButton(text = stringResource(id = R.string.word_edit), onClick = onClickEditButton)
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
      )
    }
  }
}
