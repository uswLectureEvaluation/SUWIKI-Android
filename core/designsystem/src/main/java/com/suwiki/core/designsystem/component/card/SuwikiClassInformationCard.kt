package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.button.SuwikiContainedSmallButton
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiClassInformationCard(
  modifier: Modifier = Modifier,
  className: String,
  professor: String,
  day: String,
  classPeriod: String,
  location: String,
  grade: String,
  classType: String,
  openMajor: String,
  onClick: () -> Unit,
  onClickAdd: () -> Unit,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .drawBehind {
        val strokeWidth = 1.dp.toPx()
        drawLine(
          color = GrayF6,
          start = Offset(0f, size.height - strokeWidth),
          end = Offset(size.width, size.height - strokeWidth),
          strokeWidth = strokeWidth
        )
      }
      .suwikiClickable(
        onClick = onClick,
      )
      .padding(horizontal = 24.dp, vertical = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Text(
          text = className,
          style = SuwikiTheme.typography.body6,
          color = Black,
        )
        VerticalDivider(
          color = GrayDA,
          modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .padding(vertical = 3.dp),
        )
        Text(
          text = professor,
          style = SuwikiTheme.typography.body6,
          color = Black,
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
      ) {
        Text(
          text = day,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
        Text(
          text = classPeriod,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
        Text(
          text = location,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
      ) {
        Text(
          text = grade,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
        Text(
          text = classType,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
        Text(
          text = openMajor,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
      }
    }

    SuwikiContainedSmallButton(text = stringResource(R.string.word_add), onClick = onClickAdd)
  }
}

@Preview
@Composable
fun ClassInformationPreview() {
  SuwikiTheme {
    Column {
      SuwikiClassInformationCard(
        modifier = Modifier,
        className = "강의명",
        professor = "교수명",
        day = "요일",
        classPeriod = "교시",
        location = "강의실",
        grade = "학년",
        classType = "강의유형",
        openMajor = "개설학과",
        onClick = {},
        onClickAdd = {},
      )
    }
  }
}
