package com.suwiki.feature.timetable.createtimetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.timetable.timetable.component.TimetableAppbar
import com.suwiki.feature.timetable.timetable.component.TimetableEmptyColumn
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CreateTimetableRoute(
  popBackStack: () -> Unit,
) {
  CreateTimetableScreen()
}

@Composable
fun CreateTimetableScreen() {
  Text(text = "시간표 생성")
}


@Preview
@Composable
fun CreateTimetableScreenPreview() {
  SuwikiTheme {
    CreateTimetableScreen()
  }
}
