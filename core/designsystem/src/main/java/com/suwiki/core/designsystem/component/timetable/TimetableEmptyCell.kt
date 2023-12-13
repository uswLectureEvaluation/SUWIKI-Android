package com.suwiki.core.designsystem.component.timetable

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimetableEmptyCell(
  modifier: Modifier = Modifier,
  minute: Int = MINUTE60,
  text: String? = null,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .height(timetableHeightPerHour * minute / MINUTE60)
      .border(width = timetableBorderWidth, color = Color.Gray)
      .padding(timetableBorderWidth),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    if (text != null) Text(text = text)
  }
}

@Preview(showBackground = true)
@Composable
fun TimetableEmptyCellPreview() {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
  ) {
    TimetableEmptyCell()

    TimetableEmptyCell(text = "월")
  }
}