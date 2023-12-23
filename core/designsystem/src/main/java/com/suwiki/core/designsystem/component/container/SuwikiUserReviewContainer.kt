package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.suwiki.core.designsystem.component.badge.SuwikiBlueLabel
import com.suwiki.core.designsystem.component.badge.SuwikiGrayLabel
import com.suwiki.core.designsystem.component.card.RatingBar
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiUserReviewContainer(
  modifier: Modifier = Modifier,
  text: String,
  isChecked: Boolean,
  onClick: (Boolean) -> Unit,
) {
  val buttonText = if (isChecked) stringResource(id = R.string.word_edit) else stringResource(id = R.string.word_report)
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
        ) {
          /**
           * Suwiki Componet Badge로 교체 필요
           * **/
          if (isChecked) {
            SuwikiBlueLabel(
              text = stringResource(id = R.string.word_my),
            )
            Spacer(modifier = Modifier.width(8.dp))
          }
          SuwikiGrayLabel(
            text = stringResource(id = R.string.word_semester),
          )
          Spacer(modifier = Modifier.weight(1f))
//          SuwikiContainedGreyButtonSmall(text = buttonText, onClick = { onClick(isChecked) })
        }
        RatingBar(3)
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
fun ReviewContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiUserReviewContainer(
        isChecked = false,
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClick = {},
      )
      SuwikiUserReviewContainer(
        isChecked = true,
        text = "거의 한 학기 팀플하시는데... 팀원 잘 만나면 잘 모르겠네요. 굉장히 오픈 마인드시긴해요.",
        onClick = {},
      )
    }
  }
}
