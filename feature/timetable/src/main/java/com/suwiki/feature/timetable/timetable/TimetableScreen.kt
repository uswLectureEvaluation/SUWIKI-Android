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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.feature.timetable.R
import com.suwiki.core.ui.argument.CellEditorArgument
import com.suwiki.feature.timetable.timetable.component.EditTimetableCellBottomSheet
import com.suwiki.feature.timetable.timetable.component.TimetableAppbar
import com.suwiki.feature.timetable.timetable.component.TimetableEmptyColumn
import com.suwiki.feature.timetable.timetable.component.timetable.Timetable
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType
import com.suwiki.feature.timetable.widget.sendWidgetUpdateCommand
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TimetableRoute(
    padding: PaddingValues,
    viewModel: TimetableViewModel = hiltViewModel(),
    navigateTimetableEditor: () -> Unit,
    navigateOpenLecture: () -> Unit,
    navigateTimetableList: () -> Unit,
    handleException: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    navigateCellEditor: (CellEditorArgument) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  val context = LocalContext.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is TimetableSideEffect.HandleException -> handleException(sideEffect.throwable)
      TimetableSideEffect.NavigateAddTimetableCell -> navigateOpenLecture()
      TimetableSideEffect.ShowNeedCreateTimetableToast -> onShowToast(context.getString(R.string.timetable_screen_need_create_timetable))
      TimetableSideEffect.NavigateTimetableEditor -> navigateTimetableEditor()
      is TimetableSideEffect.NavigateCellEditor -> navigateCellEditor(sideEffect.argument)
      TimetableSideEffect.NavigateTimetableList -> navigateTimetableList()
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.getMainTimetable()
  }

  LaunchedEffect(key1 = uiState.timetable) {
    sendWidgetUpdateCommand(context)
  }

  TimetableScreen(
    padding = padding,
    uiState = uiState,
    onClickAddTimetable = viewModel::navigateTimetableEditor,
    onClickAppbarAdd = viewModel::navigateAddTimetableCell,
    onClickTimetableCell = viewModel::showEditCellBottomSheet,
    onDismissEditCellBottomSheet = viewModel::hideEditCellBottomSheet,
    onClickTimetableCellDeleteButton = viewModel::deleteCell,
    onClickTimetableCellEditButton = { cell ->
      viewModel.hideEditCellBottomSheet()
      viewModel.navigateCellEdit(cell)
    },
    onDismissSelectBottomSheet = viewModel::hideSelectCellTypeBottomSheet,
    onClickSelectBottomSheetItem = { position ->
      viewModel.updateCellType(position)
      viewModel.hideSelectCellTypeBottomSheet()
    },
    onClickSetting = viewModel::showSelectCellTypeBottomSheet,
    onClickHamburger = viewModel::navigateTimetableList,
  )
}

@Composable
fun TimetableScreen(
  padding: PaddingValues,
  uiState: TimetableState = TimetableState(),
  onClickAddTimetable: () -> Unit = {},
  onClickAppbarAdd: () -> Unit = {},
  onClickTimetableCell: (TimetableCell) -> Unit = {},
  onDismissEditCellBottomSheet: () -> Unit = {},
  onClickTimetableCellDeleteButton: (TimetableCell) -> Unit = {},
  onClickTimetableCellEditButton: (TimetableCell) -> Unit = {},
  onDismissSelectBottomSheet: () -> Unit = {},
  onClickSelectBottomSheetItem: (Int) -> Unit = {},
  onClickSetting: () -> Unit = {},
  onClickHamburger: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(padding),
  ) {
    TimetableAppbar(
      name = uiState.timetable?.name,
      onClickAdd = onClickAppbarAdd,
      onClickHamburger = onClickHamburger,
      onClickSetting = onClickSetting,
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
      Timetable(
        timetable = uiState.timetable ?: com.suwiki.core.model.timetable.Timetable(),
        type = uiState.cellType,
        onClickTimetableCell = onClickTimetableCell,
      )
    }
  }

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showSelectCellTypeBottomSheet,
    onDismissRequest = onDismissSelectBottomSheet,
    onClickItem = onClickSelectBottomSheetItem,
    itemList = TimetableCellType.entries.map { stringResource(id = it.stringResId) }.toPersistentList(),
    title = stringResource(R.string.timetable_screen_select_type_cell_title),
    selectedPosition = uiState.cellType.ordinal,
  )

  EditTimetableCellBottomSheet(
    isSheetOpen = uiState.showEditCellBottomSheet,
    onDismissRequest = onDismissEditCellBottomSheet,
    cell = uiState.selectedCell,
    onClickDeleteButton = onClickTimetableCellDeleteButton,
    onClickEditButton = onClickTimetableCellEditButton,
  )
}

@Preview
@Composable
fun TimetableScreenPreview() {
  SuwikiTheme {
    TimetableScreen(padding = PaddingValues(0.dp))
  }
}
