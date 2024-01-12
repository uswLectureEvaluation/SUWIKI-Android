package com.suwiki.feature.timetable.timetable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.suwiki.feature.timetable.timetable.component.timetable.Timetable
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TimetableRoute(
  padding: PaddingValues,
  viewModel: TimetableViewModel = hiltViewModel(),
  navigateCreateTimetable: () -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  LaunchedEffect(key1 = Unit) {
    viewModel.getMainTimetable()
  }

  TimetableScreen(
    padding = padding,
    uiState = uiState,
    onClickAddTimetable = navigateCreateTimetable,
  )
}

@Composable
fun TimetableScreen(
  padding: PaddingValues,
  uiState: TimetableState = TimetableState(),
  onClickAddTimetable: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(padding),
  ) {
    TimetableAppbar(
      name = uiState.timetable?.name,
      onClickAdd = {},
      onClickHamburger = {},
      onClickSetting = {},
    )

    AnimatedVisibility(
      visible = uiState.showTimetableEmptyColumn == true,
      enter = fadeIn(),
      exit = fadeOut(),
    ) {
      TimetableEmptyColumn(
        modifier = Modifier
          .fillMaxSize()
          .background(White),
        onClickAdd = onClickAddTimetable,
      )
    }

    AnimatedVisibility(
      visible = uiState.showTimetableEmptyColumn == false,
      enter = fadeIn(),
      exit = fadeOut(),
    ) {
      Timetable(timetable = uiState.timetable!!)
    }
  }
}

@Preview
@Composable
fun TimetableScreenPreview() {
  SuwikiTheme {
    TimetableScreen(padding = PaddingValues(0.dp))
  }
}
