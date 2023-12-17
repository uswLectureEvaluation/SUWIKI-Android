package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.badge.SuwikiBasicLabel
import com.suwiki.core.designsystem.component.button.SuwikiContainedGreyButtonSmall
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiTypeExamUserReviewContainer(
  modifier: Modifier = Modifier,
  difficultyText: String,
  typeExamText: String,
  text: String,
  isChecked: Boolean,
  onClick: (Boolean) -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    contentAlignment = Alignment.Center,
  ) {
    val buttonText = if (isChecked) stringResource(id = R.string.word_edit) else stringResource(id = R.string.word_report)
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
        ) {
          if (isChecked) {
            SuwikiBasicLabel(
              text = stringResource(id = R.string.word_my),
              textColor = White,
              backgroundColor = Primary,
            )
            Spacer(modifier = Modifier.width(8.dp))
          }
          SuwikiBasicLabel(
            text = stringResource(id = R.string.word_semester),
            textColor = Gray6A,
            backgroundColor = GrayF6,
          )
          Spacer(modifier = Modifier.width(6.dp))
          SuwikiBasicLabel(
            text = stringResource(id = R.string.word_type_exam),
            textColor = Gray6A,
            backgroundColor = GrayF6,
          )
          Spacer(modifier = Modifier.weight(1f))
          SuwikiContainedGreyButtonSmall(text = buttonText, onClick = { onClick(isChecked) })
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
          Text(
            text = stringResource(id = R.string.word_difficulcy),
            style = SuwikiTheme.typography.caption2,
            color = Gray95,
          )
          Text(
            text = difficultyText,
            style = SuwikiTheme.typography.caption1,
            color = Black,
          )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text(
            text = stringResource(id = R.string.word_type_exam),
            style = SuwikiTheme.typography.caption2,
            color = Gray95,
          )
          Text(
            text = typeExamText,
            style = SuwikiTheme.typography.caption1,
            color = Black,
          )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = text,
          style = SuwikiTheme.typography.body7,
          color = Black,
        )
      }
    }
  }
}

@Preview
@Composable
fun ReviewTypeExamContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiTypeExamUserReviewContainer(
        isChecked = false,
        difficultyText = "어려움",
        typeExamText = "응용,실습,과제,PPT",
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClick = {},
      )
      SuwikiTypeExamUserReviewContainer(
        isChecked = true,
        difficultyText = "어려움",
        typeExamText = "응용,실습,과제,PPT",
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClick = {},
      )
    }
  }
}
