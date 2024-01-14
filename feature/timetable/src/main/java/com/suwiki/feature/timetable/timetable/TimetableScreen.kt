package com.suwiki.feature.timetable.timetable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.timetable.R
import com.suwiki.feature.timetable.timetable.component.TimetableAppbar
import com.suwiki.feature.timetable.timetable.component.TimetableEmptyColumn
import com.suwiki.feature.timetable.timetable.component.timetable.Timetable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TimetableRoute(
  padding: PaddingValues,
  viewModel: TimetableViewModel = hiltViewModel(),
  navigateCreateTimetable: () -> Unit,
  navigateOpenLecture: () -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  val context = LocalContext.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is TimetableSideEffect.HandleException -> handleException(sideEffect.throwable)
      TimetableSideEffect.NavigateAddTimetableCell -> navigateOpenLecture()
      TimetableSideEffect.ShowNeedCreateTimetableToast -> onShowToast(context.getString(R.string.timetable_screen_need_create_timetable))
      TimetableSideEffect.NavigateCreateTimetable -> navigateCreateTimetable()
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.getMainTimetable()
  }

  TimetableScreen(
    padding = padding,
    uiState = uiState,
    onClickAddTimetable = viewModel::navigateCreateTimetable,
    onClickAppbarAdd = viewModel::navigateAddTimetableCell,
  )
}

@Composable
fun TimetableScreen(
  padding: PaddingValues,
  uiState: TimetableState = TimetableState(),
  onClickAddTimetable: () -> Unit = {},
  onClickAppbarAdd: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(padding),
  ) {
    TimetableAppbar(
      name = uiState.timetable?.name,
      onClickAdd = onClickAppbarAdd,
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
