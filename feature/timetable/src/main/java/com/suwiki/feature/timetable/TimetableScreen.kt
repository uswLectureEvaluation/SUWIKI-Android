package com.suwiki.feature.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.timetable.component.TimetableAppbar
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TimetableRoute(
  padding: PaddingValues,
  viewModel: TimetableViewModel = hiltViewModel(),
) {
  val uiState = viewModel.collectAsState().value

  LaunchedEffect(key1 = Unit) {
    viewModel.getMainTimetable()
  }

  TimetableScreen(
    uiState = uiState
  )
}

@Composable
fun TimetableScreen(
  uiState: TimetableState = TimetableState()
) {
  Column(
    modifier = Modifier.fillMaxSize(),
  ) {
    TimetableAppbar()

    if (uiState.showTimetableEmptyColumn) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(modifier = Modifier.padding(top = 130.dp))

        Text(
          text = stringResource(R.string.timetable_screen_create_timetable),
          color = Gray95,
          style = SuwikiTheme.typography.header4,
          textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.size(28.dp))

        SuwikiContainedMediumButton(text = stringResource(R.string.timetable_screen_create_timetable_button))
      }
    }
  }

}

@Preview
@Composable
fun TimetableScreenPreview() {
  SuwikiTheme {
    TimetableRoute(padding = PaddingValues(0.dp))
  }
}
