package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiTypeExamText(
  modifier: Modifier = Modifier,
  difficultyText: String,
  typeExamText: String,
  text: String,
  isChecked: Boolean,
  onClick: (Boolean) -> Unit,
) {
  val buttonText = if (isChecked) stringResource(id = R.string.word_edit) else stringResource(id = R.string.word_report)
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
      ) {
//        if (isChecked) {
//          SuwikiBlueLabel(
//            text = stringResource(id = R.string.word_my),
//          )
//          Spacer(modifier = Modifier.width(8.dp))
//        }
//        SuwikiGrayLabel(
//          text = stringResource(id = R.string.word_semester),
//        )
//        Spacer(modifier = Modifier.width(6.dp))
//        SuwikiGrayLabel(
//          text = stringResource(id = R.string.word_type_exam),
//        )
        Spacer(modifier = Modifier.weight(1f))
      }
      Spacer(modifier = Modifier.height(10.dp))
      SuwikiTypeExamText(
        modifier = Modifier.fillMaxWidth(),
        titleText = stringResource(id = R.string.word_difficulcy),
        contentText = difficultyText,
      )
      Spacer(modifier = Modifier.height(2.dp))
      SuwikiTypeExamText(
        modifier = Modifier.fillMaxWidth(),
        titleText = stringResource(id = R.string.word_type_exam),
        contentText = typeExamText,
      )
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = text,
        style = SuwikiTheme.typography.body7,
        color = Black,
      )
    }
  }
}

@Composable
fun SuwikiTypeExamText(
  modifier: Modifier = Modifier,
  titleText: String,
  contentText: String,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(2.dp),
  ) {
    Text(
      text = titleText,
      style = SuwikiTheme.typography.caption2,
      color = Gray95,
    )
    Text(
      text = contentText,
      style = SuwikiTheme.typography.caption1,
      color = Black,
    )
  }
}

@Preview
@Composable
fun ReviewTypeExamContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiTypeExamText(
        isChecked = false,
        difficultyText = "어려움",
        typeExamText = "응용,실습,과제,PPT",
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClick = {},
      )
      SuwikiTypeExamText(
        isChecked = true,
        difficultyText = "어려움",
        typeExamText = "응용,실습,과제,PPT",
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClick = {},
      )
    }
  }
}
