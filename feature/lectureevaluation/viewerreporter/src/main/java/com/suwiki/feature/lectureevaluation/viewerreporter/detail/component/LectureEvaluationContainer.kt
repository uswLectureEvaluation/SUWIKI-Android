package com.suwiki.feature.lectureevaluation.viewerreporter.detail.component

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
import com.suwiki.feature.common.designsystem.component.badge.BadgeColor
import com.suwiki.feature.common.designsystem.component.badge.SuwikiBadge
import com.suwiki.feature.common.designsystem.component.button.SuwikiContainedSmallButton
import com.suwiki.feature.common.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.feature.common.designsystem.theme.Black
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.R

@Composable
fun LectureEvaluationContainer(
  modifier: Modifier = Modifier,
  content: String,
  semester: String,
  rating: Float,
  isAuthor: Boolean = false,
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
        text = semester,
      )
      Spacer(modifier = Modifier.weight(1f))
      SuwikiContainedSmallButton(text = buttonText, onClick = onClickButton)
    }
    SuwikiRatingBar(rating = rating)
    Text(
      text = content,
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
      LectureEvaluationContainer(
        isAuthor = false,
        semester = "2023-1",
        rating = 3.0f,
        content = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
      )
      LectureEvaluationContainer(
        rating = 3.0f,
        semester = "2023-1",
        isAuthor = true,
        content = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
      )
    }
  }
}
