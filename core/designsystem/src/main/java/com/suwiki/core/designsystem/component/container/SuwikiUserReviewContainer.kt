package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.badge.BadgeColor
import com.suwiki.core.designsystem.component.badge.SuwikiBadge
import com.suwiki.core.designsystem.component.button.SuwikiContainedSmallButton
import com.suwiki.core.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiUserReviewContainer(
  modifier: Modifier = Modifier,
  text: String,
  isAuthor: Boolean,
  onClickButton: () -> Unit = {},
) {
  val buttonText = if (isAuthor) stringResource(id = R.string.word_edit) else stringResource(id = R.string.word_report)
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      if (isAuthor) {
        SuwikiBadge(
          color = BadgeColor.Blue,
          text = stringResource(id = R.string.word_my),
        )
      }
      SuwikiBadge(
        color = BadgeColor.Gray,
        text = stringResource(id = R.string.word_semester),
      )
      Spacer(modifier = Modifier.weight(1f))
      SuwikiContainedSmallButton(text = buttonText, onClick = onClickButton)
    }
    SuwikiRatingBar(rating = 3.0f)
    Text(
      text = text,
      style = SuwikiTheme.typography.body7,
      color = Black,
    )
  }
}

@Preview
@Composable
fun ReviewContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiUserReviewContainer(
        isAuthor = false,
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
      )
      SuwikiUserReviewContainer(
        isAuthor = true,
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
      )
    }
  }
}