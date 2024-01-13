package com.suwiki.feature.timetable.addcell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.container.SuwikiSelectionContainer
import com.suwiki.core.designsystem.component.textfield.SuwikiRegularTextField
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.timetable.R
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun AddTimetableCellRoute(
  selectedOpenMajor: String,
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  Timber.d("$selectedOpenMajor")

  AddTimetableCellScreen(
  )
}

@Composable
fun AddTimetableCellScreen(
) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(White),
  ) {
    SuwikiAppBarWithTitle(
      showCloseIcon = false,
      onClickBack = {},
    )

    Spacer(modifier = Modifier.size(20.dp))

    Column(
      modifier = Modifier.padding(24.dp),
    ) {

    }
  }
}

@Preview
@Composable
fun AddTimetableCellScreenPreview() {
  SuwikiTheme {
    AddTimetableCellScreen()
  }
}
