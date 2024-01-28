package com.suwiki.feature.timetable.openlecture.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
fun OpenLectureCard(
  modifier: Modifier = Modifier,
  className: String,
  professor: String,
  cellInfo: String,
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
          strokeWidth = strokeWidth,
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
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Text(
          modifier = Modifier.weight(1f, fill = false),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
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
          maxLines = 1,
          text = professor,
          overflow = TextOverflow.Ellipsis,
          style = SuwikiTheme.typography.body6,
          color = Black,
        )
      }

      Text(
        text = cellInfo,
        style = SuwikiTheme.typography.caption4,
        color = Gray6A,
      )

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
          maxLines = 1,
          text = openMajor,
          style = SuwikiTheme.typography.caption4,
          color = Gray6A,
        )
      }
    }

    SuwikiContainedSmallButton(text = stringResource(R.string.word_add), onClick = onClickAdd)
  }
//    HorizontalDivider(
//      thickness = 1.dp,
//      color = GrayF6,
//    )
}

@Preview
@Composable
fun OpenLectureCardPreview() {
  SuwikiTheme {
    Column {
      OpenLectureCard(
        modifier = Modifier,
        className = "강의명 강의명 강의명 강의명 강의명 강의명 강의명 강의명 강의명 강의명 강의명",
        professor = "교수명 교수명 교수명 교수명 교수명",
        cellInfo = "목 6,7교시 (미래211) 목 6,7교시 (미래211) 목 6,7교시 (미래211) 목 6,7교시 (미래211)",
        grade = "학년",
        classType = "강의유형",
        openMajor = "개설학과 개설학과 개설학과 개설학과 개설학과 개설학과",
        onClick = {},
        onClickAdd = {},
      )
    }
  }
}
