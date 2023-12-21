package com.suwiki.feature.timetable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun TimetableScreen(
  padding: PaddingValues,
) {
  Text(text = "시간표")
}

@Preview
@Composable
fun TimetableScreenPreview() {
  SuwikiTheme {
    TimetableScreen(padding = PaddingValues(0.dp))
  }
}
