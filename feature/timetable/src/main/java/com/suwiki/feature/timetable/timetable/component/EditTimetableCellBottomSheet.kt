package com.suwiki.feature.timetable.timetable.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheet
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.timetable.R
import com.suwiki.feature.timetable.timetable.component.timetable.toText

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditTimetableCellBottomSheet(
  onDismissRequest: () -> Unit = {},
  cell: TimetableCell = TimetableCell(color = TimetableCellColor.GRAY_DARK),
  isSheetOpen: Boolean = false,
  onClickDeleteButton: (TimetableCell) -> Unit = {},
  onClickEditButton: (TimetableCell) -> Unit = {},
) {
  SuwikiBottomSheet(
    isSheetOpen = isSheetOpen,
    onDismissRequest = onDismissRequest,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        Text(text = cell.name, style = SuwikiTheme.typography.header2)
        Text(modifier = Modifier.align(Alignment.Bottom), text = cell.professor, style = SuwikiTheme.typography.header6)
      }

      Text(
        text = "${cell.location} (${cell.day.toText()} ${cell.startPeriod} - ${cell.endPeriod}교시)",
        style = SuwikiTheme.typography.body3,
      )

      Spacer(modifier = Modifier.size(4.dp))

      Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        SuwikiContainedLargeButton(
          modifier = Modifier.weight(1f),
          enabled = false,
          text = stringResource(R.string.word_do_delete),
          onClick = { onClickDeleteButton(cell) },
        )

        SuwikiContainedLargeButton(
          modifier = Modifier.weight(1f),
          text = stringResource(R.string.word_do_edit),
          onClick = { onClickEditButton(cell) },
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun EditTimetableCellBottomSheetPreview() {
  SuwikiTheme {
    EditTimetableCellBottomSheet(
      cell = TimetableCell(
        color = TimetableCellColor.GRAY_DARK,
        id = "",
        name = "도전과창조",
        professor = "우문균",
        location = "미래202",
        day = TimetableDay.MON,
        startPeriod = 3,
        endPeriod = 6,
        credit = "",
      ),
    )
  }
}
