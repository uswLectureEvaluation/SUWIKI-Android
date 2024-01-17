package com.suwiki.feature.timetable.timetablelist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTextButton
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.core.designsystem.component.container.SuwikiReviewEditContainer
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.timetable.R
import com.suwiki.feature.timetable.timetablelist.component.TimetableEditContainer
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TimetableListRoute(
  viewModel: TimetableListListViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  handleException: (Throwable) -> Unit = {},
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is TimetableListSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  TimetableListScreen(
    uiState = uiState,
  )
}

@Composable
fun TimetableListScreen(
  uiState: TimetableListState = TimetableListState(),
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTextButton(
      buttonText = stringResource(id = R.string.word_add),
    )

    TimetableEditContainer()
  }
}

@Preview
@Composable
fun TimetableListScreenPreview() {
  SuwikiTheme {
    TimetableListScreen()
  }
}
