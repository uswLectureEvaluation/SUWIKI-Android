package com.suwiki.core.designsystem.component.timetable.cell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.timetable.timetable.component.timetable.MINUTE60
import com.suwiki.feature.timetable.timetable.component.timetable.timetableBorderWidth
import com.suwiki.feature.timetable.timetable.component.timetable.timetableHeightPerHour

@Composable
internal fun EmptyCell(
  modifier: Modifier = Modifier,
  minute: Int = MINUTE60,
  text: String? = null,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .height(timetableHeightPerHour * minute / MINUTE60)
      .border(width = timetableBorderWidth, color = GrayF6)
      .background(White)
      .padding(timetableBorderWidth),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    if (text != null) {
      Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = SuwikiTheme.typography.caption4,
        color = Gray6A,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun TimetableEmptyCellPreview() {
  SuwikiTheme {
    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      EmptyCell()

      EmptyCell(text = "월")
    }
  }
}
