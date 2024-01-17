package com.suwiki.feature.timetable.timetablelist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
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
import com.suwiki.core.designsystem.component.badge.BadgeColor
import com.suwiki.core.designsystem.component.badge.SuwikiBadge
import com.suwiki.core.designsystem.component.button.SuwikiContainedSmallButton
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun TimetableEditContainer(
  modifier: Modifier = Modifier,
  timetable: Timetable = Timetable(),
  onClickEditButton: () -> Unit = {},
  onClickDeleteButton: () -> Unit = {},
  onClick: () -> Unit = {},
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
      .suwikiClickable(onClick = onClick)
      .padding(24.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Row(
      modifier = Modifier
        .weight(1f, false)
        .wrapContentHeight()
        .padding(end = 24.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f, false),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = timetable.name,
        style = SuwikiTheme.typography.header6,
        color = Black,
      )

      SuwikiBadge(color = BadgeColor.Gray, text = "${timetable.year}-${timetable.semester}")
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiContainedSmallButton(
        text = stringResource(id = R.string.word_edit),
        onClick = onClickEditButton,
      )
      SuwikiContainedSmallButton(
        text = stringResource(id = R.string.word_delete),
        onClick = onClickDeleteButton,
      )
    }
  }
}

@Composable
@Preview
fun TimetableEditContainerPreview() {
  SuwikiTheme {
    Column {
      TimetableEditContainer(
        timetable = Timetable(
          createTime = 0,
          year = "2024",
          semester = "1",
          name = "시간표시간표시간표시간표시간표시간표시간표시간표",
          cellList = listOf(),
        ),
      )
    }
  }
}
