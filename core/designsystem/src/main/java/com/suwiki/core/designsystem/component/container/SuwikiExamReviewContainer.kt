package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiExamReviewContainer(
  modifier: Modifier = Modifier,
  difficulty: String,
  examType: String,
  content: String,
  isAuthor: Boolean = false,
  onClickButton: () -> Unit,
) {
  val buttonText = if (isAuthor) stringResource(id = R.string.word_edit) else stringResource(id = R.string.word_report)

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(White)
      .padding(24.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
    ) {
      if (isAuthor) {
        SuwikiBadge(
          color = BadgeColor.Blue,
          text = stringResource(id = R.string.word_my),
        )
        Spacer(modifier = Modifier.width(8.dp))
      }
      SuwikiBadge(
        color = BadgeColor.Gray,
        text = stringResource(id = R.string.word_semester),
      )
      Spacer(modifier = Modifier.width(6.dp))
      SuwikiBadge(
        color = BadgeColor.Gray,
        text = stringResource(id = R.string.word_type_exam),
      )
      Spacer(modifier = Modifier.weight(1f))
      SuwikiContainedSmallButton(text = buttonText, onClick = onClickButton)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Text(
        text = stringResource(id = R.string.word_difficulcy),
        style = SuwikiTheme.typography.caption2,
        color = Gray95,
      )
      Text(
        text = difficulty,
        style = SuwikiTheme.typography.caption1,
        color = Black,
      )
    }
    Spacer(modifier = Modifier.height(2.dp))
    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Text(
        text = stringResource(id = R.string.word_type_exam),
        style = SuwikiTheme.typography.caption2,
        color = Gray95,
      )
      Text(
        text = examType,
        style = SuwikiTheme.typography.caption1,
        color = Black,
      )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
      text = content,
      style = SuwikiTheme.typography.body7,
      color = Black,
    )
  }
}

@Preview
@Composable
fun SuwikiExamReviewContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiExamReviewContainer(
        isAuthor = false,
        difficulty = "어려움",
        examType = "응용,실습,과제,PPT",
        content = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClickButton = {},
      )
      SuwikiExamReviewContainer(
        isAuthor = true,
        difficulty = "어려움",
        examType = "응용,실습,과제,PPT",
        content = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClickButton = {},
      )
    }
  }
}
